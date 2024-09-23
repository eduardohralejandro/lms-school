package com.lms_schoolapp.greenteam.social.model;

import com.lms_schoolapp.greenteam.model.Post;
import com.lms_schoolapp.greenteam.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "thread")
public class ThreadRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn(name = "forum_id")
    private Forum forum;
    @OneToMany(mappedBy = "thread")
    Set<Post> posts = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false, name = "creation_date")
    private LocalDateTime createdDate;

    @Override
    public String toString() {
        return String.format("Thread: %s \n-%s %s %02d:%02d %s %s %d",
                title,
                user.getFirstName(),
                user.getLastName(),
                createdDate.getHour(),
                createdDate.getMinute(),
                createdDate.getDayOfWeek(),
                createdDate.getMonth(),
                createdDate.getYear()
        );
    }
}
