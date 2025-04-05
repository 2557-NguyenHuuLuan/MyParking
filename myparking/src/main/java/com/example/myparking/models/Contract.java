package com.example.myparking.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "CONTRACT")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "written_on")
    private Date writtenOn;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "value")
    private Long value;

    @Column(name = "payment_status", nullable = false)
    private boolean paymentStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getWrittenOn() {
        return writtenOn;
    }

    public void setWrittenOn(Date writtenOn) {
        this.writtenOn = writtenOn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
