package com.healontrip.service.impl;

import com.healontrip.dto.MembershipDto;
import com.healontrip.entity.MembershipEntity;
import com.healontrip.exception.ResourceNotFoundException;
import com.healontrip.repository.MembershipRepository;
import com.healontrip.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MembershipServiceImpl implements MembershipService {
    @Autowired
    private MembershipRepository membershipRepository;

    @Override
    public void createMembership(MembershipDto membershipDto) {
        MembershipEntity membershipEntity = new MembershipEntity();

        membershipEntity.setUserId(membershipDto.getUserId());
        membershipEntity.setName(membershipDto.getName());

        membershipRepository.save(membershipEntity);
    }

    @Override
    public void updateMembership(MembershipDto membershipDto) {
        MembershipEntity membershipEntity = findById(membershipDto.getId());

        membershipEntity.setUserId(membershipDto.getUserId());
        membershipEntity.setName(membershipDto.getName());

        membershipRepository.save(membershipEntity);
    }

    @Override
    public void deleteMembership(Long id) {
        MembershipEntity membershipEntity = findById(id);

        membershipRepository.delete(membershipEntity);
    }

    @Override
    public List<MembershipDto> getMembershipList(Long userId) {
        List<MembershipEntity> membershipEntityList = membershipRepository.findMembershipByUserId(userId);
        List<MembershipDto> membershipDtoList = new ArrayList<>();

        for (MembershipEntity membershipEntity: membershipEntityList) {
            MembershipDto membershipDto = new MembershipDto();

            membershipDto.setId(membershipEntity.getId());
            membershipDto.setUserId(membershipEntity.getUserId());
            membershipDto.setName(membershipEntity.getName());

            membershipDtoList.add(membershipDto);
        }

        return membershipDtoList;
    }

    @Override
    public MembershipEntity findById(Long id) {
        return membershipRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Membership not found with id: " + id));
    }
}
