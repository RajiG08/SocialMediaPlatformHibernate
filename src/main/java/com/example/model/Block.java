
package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "block",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "friend_id"})})
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "block_id")
    private int blockId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
    private User friend;

    // Getters and Setters
    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public Block() {
        super();
    }

    public Block(User user, User friend) {
        this.user = user;
        this.friend = friend;
    }
}
