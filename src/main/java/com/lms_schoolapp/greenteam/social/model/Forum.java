package com.lms_schoolapp.greenteam.social.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ForumType forumType;
    @OneToMany(mappedBy = "forum")
    private Set<ThreadRoom> threads = new HashSet<>();

    @Override
    public String toString() {
        return "Forum for: " + forumType.name();
    }
}
