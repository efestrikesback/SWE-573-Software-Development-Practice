package com.devcom.user;


import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum CommunityRole {
    MEMBER,
    CREATOR
}
