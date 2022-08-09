package com.example.persistence;

import com.example.domain.Policy;

public interface PolicyLookup {
    Policy getPolicy(String number);
    Policy createPolicy(Policy policy);
}
