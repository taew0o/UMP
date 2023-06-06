package ppkjch.ump.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ppkjch.ump.dto.EvaluateAppointmentDTO;
import ppkjch.ump.entity.*;
import ppkjch.ump.exception.RoomFullException;
import ppkjch.ump.repository.*;
import java.util.ArrayList;
import java.util.List;


//@EnableScheduling
//@EnableAsync
@RequiredArgsConstructor
@Service
public class AppointmentService {
    private final JpaAppointmentChattingRoomRepository jpaAppointmentChattingRoomRepository;
    private final JpaChattingRoomRepository jpaChattingRoomRepository;
    private final JpaMessageRepository jpaMessageRepository;
    private final JpaAppointmentEvaluationRepository jpaAppointmentEvaluationRepository;
    private final JpaUserRepository jpaUserRepository;
    @Transactional
    public Long saveAppointment(List<User> users, String roomName, Long createTime, String time, String location){
        int numPerson = users.size();
        if(numPerson > 10){
            throw new RoomFullException("10명 이하의 유저를 선택해주십시오.");
        }
        List<UserChattingRoom> userChattingRooms = new ArrayList<>();
        for (User user: users) { //각 유저ID로 User 찾아 UserChattingroom객체 만들어 매핑
            UserChattingRoom userChattingRoom = new UserChattingRoom();
            userChattingRoom.setEnterTime(createTime);
            userChattingRoom.setUser(user);
            userChattingRooms.add(userChattingRoom);
        }

        //약속 채팅방 생성
        AppointmentChattingRoom acr = AppointmentChattingRoom.createAppointmentChattingRoom(numPerson,userChattingRooms,roomName, time, location);
        jpaAppointmentChattingRoomRepository.save(acr);
        System.out.println("time = " + time);
        return acr.getId();
    }

    @Transactional
    public void goOutRoom(User u, AppointmentChattingRoom acr){

        //userChattingroom DB에서 지우고 채팅방 인원 수를 갱신
        jpaAppointmentChattingRoomRepository.goOutRoom(u,acr);
        acr.updateNumPerson(-1);

        //만약 빈방이 되었다면 해당 방의 메세지 및 방 정보 삭제
        if(acr.isEmptyRoom()){
            System.out.println(" = asdasdsaasdasdakldldksd" );
            List<Message> messages = jpaMessageRepository.findMessageByRoom(acr);
            for (Message m: messages) {
                jpaMessageRepository.removeMessage(m);
            }

            //모든 유저에 대해 가장 큰 것을 판별하여 해당 User에 넣어줌
            List<AppointmentEvaluation> appointmentEvaluations = jpaAppointmentEvaluationRepository.findAppointmentEvaluationByRoom(acr);
            System.out.println("appointmentEvaluations.size() = " + appointmentEvaluations.size());
            for (AppointmentEvaluation ae:appointmentEvaluations) {
                //maxScore를 판단하여 유저에 Score갱신
                System.out.println("ae.getSumAttend() = " + ae.getSumAttend());
                System.out.println("ae.getSumAttend() = " + ae.getSumNotAttend());
                System.out.println("ae.getSumAttend() = " + ae.getSumLate());
                int maxScore = ae.getMaxScore();
                User user = ae.getUser();
                user.updateAppointmentScore(maxScore);
                jpaUserRepository.mergeUser(user);
                System.out.println("ae.getUser() = " + ae.getUser().getAppointmentScore());
            }


            //평가테이블, 약속방 모두 삭제
            jpaAppointmentEvaluationRepository.removeAppointmentEvaluations(acr);
            jpaAppointmentChattingRoomRepository.removeRoom(acr);
        }

    }


    public List<AppointmentChattingRoom> findAppointments(User user){
        return jpaAppointmentChattingRoomRepository.findAppointmentByUser(user);
    }

    public AppointmentChattingRoom findAppointmentChattingRoom(Long roomId){
        return jpaAppointmentChattingRoomRepository.findOne(roomId);
    }

    public List<User> findUserByAppointmentChattingRoom(AppointmentChattingRoom apcr){
        List<User> result =  new ArrayList<>();
        List<AppointmentEvaluation> appointmentEvaluationList = jpaAppointmentEvaluationRepository.findAppointmentEvaluationByRoom(apcr);
        for(AppointmentEvaluation ape : appointmentEvaluationList){
            result.add(ape.getUser());
        }
        return result;
    }

    @Transactional
    public void saveAppointmentEvaluation(List<User> users, AppointmentChattingRoom appointmentChattingRoom ){
        List<AppointmentEvaluation> appointmentEvaluationList = new ArrayList<>();

        for (User u: users) {
            AppointmentEvaluation appointmentEvaluation = new AppointmentEvaluation();
            appointmentEvaluation.setUser(u);
            appointmentEvaluation.setAppointmentchattingRoom(appointmentChattingRoom);
            appointmentEvaluationList.add(appointmentEvaluation);
            jpaAppointmentEvaluationRepository.save(appointmentEvaluation);
        }

    }

    public AppointmentEvaluation findAppointmentEvaluation(User user, AppointmentChattingRoom appointmentChattingRoom){
        return jpaAppointmentEvaluationRepository.findAppointmentEvaluationByRoomAndUser(appointmentChattingRoom, user);
    }

}
