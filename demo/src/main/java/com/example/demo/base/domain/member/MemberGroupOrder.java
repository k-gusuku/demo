package com.example.demo.base.domain.member;

import javax.validation.GroupSequence;

@GroupSequence({MemberValidGroup1.class, MemberGroup2.class})
public interface MemberGroupOrder {
}
