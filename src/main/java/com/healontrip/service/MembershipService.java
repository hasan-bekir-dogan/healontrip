package com.healontrip.service;

import com.healontrip.dto.MembershipDto;
import com.healontrip.entity.MembershipEntity;

import java.util.List;

public interface MembershipService {
    void createMembership(MembershipDto membershipDto);
    void updateMembership(MembershipDto membershipDto);
    void deleteMembership(Long id);
    List<MembershipDto> getMembershipList(Long userId);
    MembershipEntity findById(Long id);
}
