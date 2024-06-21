package com.bb.accountbook.domain.couple.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
import com.bb.accountbook.common.model.status.CoupleStatus;
import com.bb.accountbook.common.model.status.UserCoupleStatus;
import com.bb.accountbook.domain.couple.dto.CoupleConnectionInfoResponseDto;
import com.bb.accountbook.domain.couple.dto.CoupleStatusFindResponseDto;
import com.bb.accountbook.domain.couple.repository.CoupleRepository;
import com.bb.accountbook.domain.user.service.UserService;
import com.bb.accountbook.entity.Couple;
import com.bb.accountbook.entity.User;
import com.bb.accountbook.entity.UserCouple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bb.accountbook.common.model.codes.ErrorCode.*;
import static com.bb.accountbook.common.model.status.UserCoupleStatus.ACTIVE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CoupleService {

    private final CoupleRepository coupleRepository;

    private final UserService userService;


    @Transactional(readOnly = true)
    public UserCouple findUserCouple(Long userCoupleId) {
        return coupleRepository.findUserCoupleById(userCoupleId).orElseThrow(() -> {
            log.debug("{}.{}({}): {}", this.getClass().getName(), "findUserCouple", userCoupleId, ERR_CPL_003.getValue());
            return new GlobalException(ERR_CPL_003);
        });
    }

    public Long joinCouple(String userEmail, Long coupleId, String nickname) {
        User user = userService.findUserByEmail(userEmail);
        Couple couple = findCouple(coupleId);

        UserCouple userCouple = coupleRepository.findUserCoupleByUserEmailAndCoupleId(userEmail, coupleId)
                .orElseGet(() -> coupleRepository.saveUserCouple(new UserCouple(user, couple, nickname)));

        return userCouple.getId();
    }


    @Transactional(readOnly = true)
    public Couple findCouple(Long coupleId) {
        return coupleRepository.findById(coupleId).orElseThrow(() -> {
            log.debug("{}.{}({}): {}", this.getClass().getName(), "findCouple", coupleId, ERR_CPL_001.getValue());
            return new GlobalException(ERR_CPL_001);
        });
    }

    public void inviteToCouple(String opponentEmail, Long coupleId) {
        List<UserCouple> userCouples = coupleRepository.findUserCouplesByCoupleId(coupleId);

        Set<User> usersInCouple = userCouples.stream().map(UserCouple::getUser).collect(Collectors.toSet());

        if (usersInCouple.size() != 1) {
            log.debug("{}.{}({}, {}): {}", this.getClass().getName(), "inviteToCouple", opponentEmail, coupleId, ERR_SYS_000.getValue());
            throw new GlobalException(ERR_SYS_000);
        }

        User opponent = userService.findUserByEmail(opponentEmail);
        if (usersInCouple.contains(opponent)) {
            log.error(ERR_CPL_002.getValue());
            throw new GlobalException(ERR_CPL_002);
        }

        // TODO 상대방에게 알림 전송


        joinCouple(opponent.getEmail(), coupleId, null);
    }

    /**
     * 1. 커플 Entity 확인
     * -> API 호출자와 매핑되어 있는 Couple 엔티티가 없는 경우 새로운 커플 Entity 생성
     * 3. 그룹에 참여
     * 4. 상대방 그룹에 초대
     *
     * @param apiCallerEmail
     * @param opponentEmail
     * @param nickname
     * @return userCoupleId
     */
    public Long connectToOpponent(String apiCallerEmail, String opponentEmail, String nickname, String coupleName) {

        coupleRepository.findCoupleByUserEmail(apiCallerEmail).ifPresent(couple -> {
            log.debug("{} ====== {}", ERR_CPL_002.getValue(), apiCallerEmail);
            throw new GlobalException(ERR_CPL_002);
        });


        try {
            userService.findUserByEmail(opponentEmail);
        } catch (GlobalException e) {
            log.debug("{} ====== {}", ERR_USR_000, opponentEmail);
            throw new GlobalException(ERR_CPL_000);
        }

        coupleRepository.findCoupleByUserEmail(opponentEmail).ifPresent(couple -> {
            log.debug("상대가 이미 커플로 등록되어 있습니다.", opponentEmail);
            throw new GlobalException(ERR_CPL_005);
        });

        Couple couple = new Couple(coupleName);
        coupleRepository.save(couple);

        // userCouple mapping 생성
        UserCouple callersUserCouple = findUserCouple(joinCouple(apiCallerEmail, couple.getId(), nickname));

        callersUserCouple.changeStatus(ACTIVE);

        // 상대방 그룹에 초대
        inviteToCouple(opponentEmail, couple.getId());

        return callersUserCouple.getId();
    }

    /**
     * userCouple 객체 상태 변경
     *
     * @param userCoupleId
     * @param nickname
     * @return
     */
    public Long applyConnectRequest(Long userCoupleId, String nickname) {
        UserCouple userCouple = findUserCouple(userCoupleId);
        userCouple.changeStatus(ACTIVE);
        userCouple.changeNickname(nickname);

        userCouple.getCouple().changeStatus(CoupleStatus.ACTIVE);

        return userCouple.getId();
    }

    @Transactional(readOnly = true)
    public Couple findCoupleByUserId(Long userId) {
        return coupleRepository.findCoupleByUserId(userId).orElseThrow(() -> {
            log.debug("{}.{}({}): {}", this.getClass().getName(), "findCoupleByUserId", userId, ERR_CPL_001.getValue());
            return new GlobalException(ERR_CPL_001);
        });
    }

    @Transactional(readOnly = true)
    public boolean isActiveCouple(String email) {
        Optional<UserCouple> optional = coupleRepository.findUserCoupleByUserEmail(email);
        return optional.isPresent() && optional.get().getStatus() == ACTIVE;
    }

    @Transactional(readOnly = true)
    public Couple findCoupleByUserEmail(String email) {
        return coupleRepository.findCoupleByUserEmail(email).orElseThrow(() -> {
            log.debug("{}.{}({}): {}", this.getClass().getName(), "findCoupleByUserEmail", email, ERR_CPL_001.getValue());
            return new GlobalException(ERR_CPL_001);
        });
    }

    @Transactional(readOnly = true)
    public CoupleStatusFindResponseDto findCoupleAndUserCoupleStatus(String email) {
        return coupleRepository.findUserCoupleByUserEmail(email)
                .stream()
                .findFirst()
                .map(userCouple -> new CoupleStatusFindResponseDto(userCouple.getCouple().getStatus(), userCouple.getStatus()))
                .orElseGet(() -> new CoupleStatusFindResponseDto(CoupleStatus.NONE, UserCoupleStatus.NONE));
    }

    @Transactional(readOnly = true)
    public CoupleConnectionInfoResponseDto findCoupleConnectionInfo(String email) {
        Couple couple = coupleRepository.findCoupleByUserEmail(email).orElseThrow(() -> {
            log.debug("연결된 커플 정보를 찾을 수 없습니다. ====== {}", email);
            throw new GlobalException(ERR_CPL_007);
        });

        List<UserCouple> userCouples = couple.getUserCouples();
        if (userCouples.size() != 2) {
            log.debug("커플 정보가 정확하지 않습니다. ====== {}", email);
            throw new GlobalException(ERR_CPL_006);
        }

//        UserCouple opponentUserCouple = userCouples.stream()
//                .filter(userCouple ->
//                .findFirst()
//                .orElseThrow(() -> {
//                    log.debug("연결된 커플 정보를 찾을 수 없습니다. ====== {}", email);
//                    throw new GlobalException(ERR_CPL_001);
//                });

        UserCouple apiCallerUserCouple = null;
        UserCouple opponentUserCouple = null;

        for (UserCouple userCouple : userCouples) {
            if (userCouple.getUser().getEmail().equals(email)) {
                apiCallerUserCouple = userCouple;
            } else {
                opponentUserCouple = userCouple;
            }
        }

        if (apiCallerUserCouple == null || opponentUserCouple == null) {
            log.debug("{} ====== {}", ERR_CPL_006.getValue(), email);
            throw new GlobalException(ERR_CPL_006);
        }

        if (opponentUserCouple.getStatus() != ACTIVE) {
            log.debug("{} ====== {}", ERR_CPL_000.getValue(), email);
            throw new GlobalException((ERR_CPL_000));
        }

        return new CoupleConnectionInfoResponseDto(
                apiCallerUserCouple.getId()
                , opponentUserCouple.getStatus()
                , opponentUserCouple.getUser().getEmail()
                , opponentUserCouple.getNickname()
                , couple.getName());
    }
}
