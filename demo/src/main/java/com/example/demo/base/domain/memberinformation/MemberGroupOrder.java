package com.example.demo.base.domain.memberinformation;

import javax.validation.GroupSequence;

@GroupSequence({MemberValidGroup1.class, MemberGroup2.class})
public interface MemberGroupOrder {
}
