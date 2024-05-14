package com.bb.accountbook.domain.couple.service;

import com.bb.accountbook.common.exception.GlobalException;
import com.bb.accountbook.common.model.codes.ErrorCode;
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

    public List<UserCouple> findUserCouples(Long userId) {
        List<UserCouple> userCouplesByUserId = coupleRepository.findUserCouplesByUserId(userId);
        return userCouplesByUserId;
    }

    public Long joinCouple(Long userId, Long coupleId, String nickname) {
        User user = userService.findUserById(userId);
        Couple couple = this.findCouple(coupleId);

        if (coupleRepository.isExistUserCouple(userId, coupleId)) {
            log.error(ERR_CPL_002.getValue());
            throw new GlobalException(ERR_CPL_002);
        }

        UserCouple savedUserCouple = coupleRepository.saveUserCouple(new UserCouple(user, couple, nickname));

        return savedUserCouple.getId();
    }

    public Long createCouple(String coupleName) {
        Couple createdCouple = coupleRepository.save(new Couple(coupleName));
        return createdCouple.getId();
    }

    @Transactional(readOnly = true)
    public Couple findCouple(Long coupleId) {
        return coupleRepository.findById(coupleId).orElseThrow(() -> {
            log.error(ERR_CPL_001.getValue());
            return new GlobalException(ERR_CPL_001);
        });
    }


    /**
     * 1. 커플 Entity 확인
     *     -> API 호출자와 매핑되어 있는 Couple 엔티티가 없는 경우 새로운 커플 Entity 생성
     * 3. 그룹에 참여
     * 4. 상대방 그룹에 초대
     *
     * @param apiCallerId
     * @param opponentEmail
     * @param nickname
     * @return userCoupleId
     */
    public Long connectToOpponent(Long apiCallerId, String opponentEmail, String nickname, String coupleName) {
        Couple couple = coupleRepository.findCoupleByUserId(apiCallerId)
                .orElse(findCouple(createCouple(coupleName)));

        // 그룹에 참여
        UserCouple callersUserCouple = coupleRepository.findUserCouple(joinCouple(apiCallerId, couple.getId(), nickname));
        callersUserCouple.changeStatus(ACTIVE);

        // 상대방 그룹에 초대
        inviteToCouple(opponentEmail, couple.getId());

        return callersUserCouple.getId();
    }

    public void inviteToCouple(String opponentEmail, Long coupleId) {
        List<UserCouple> userCouples = coupleRepository.findUserCouplesByCoupleId(coupleId);

        Set<User> usersInCouple = userCouples.stream().map(UserCouple::getUser).collect(Collectors.toSet());

        if (usersInCouple.size() != 1) {
            log.error(ERR_CPL_000.getValue());
            throw new GlobalException(ERR_SYS_000);
        }

        User opponent = userService.findUserByEmail(opponentEmail);
        if (usersInCouple.contains(opponent)) {
            log.error(ERR_CPL_002.getValue());
            throw new GlobalException(ERR_CPL_002);
        }

        // TODO 상대방에게 알림 전송


        joinCouple(opponent.getId(), coupleId, null);
    }

}
