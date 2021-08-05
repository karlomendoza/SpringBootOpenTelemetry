package com.example.demo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.usertype.UserType;

@Entity(name = "USER")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  @Column(name = "ID")
  private Long id;
  @Column(name = "USER_NAME")
  private String userName;
  @Column(name = "PASSWORD")
  private String password;
  @Temporal(value = TemporalType.TIMESTAMP)
  @Column(name = "CREATED_TIME")
  private Date creationTime;
  @Temporal(value = TemporalType.TIMESTAMP)
  @Column(name = "UPDATED_TIME")
  private Date updatedTime;
  @Temporal(value = TemporalType.DATE)
  @Column(name = "DOB")
  private Date dateofBirth;
  @Column(name = "USER_TYPE")
  private String userType;
  @Transient
  private String dateOfBirthString;
  // Setters and Getters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public Date getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(Date updatedTime) {
    this.updatedTime = updatedTime;
  }

  public Date getDateofBirth() {
    return dateofBirth;
  }

  public void setDateofBirth(Date dateofBirth) {
    this.dateofBirth = dateofBirth;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public String getDateOfBirthString() {
    return dateOfBirthString;
  }

  public void setDateOfBirthString(String dateOfBirthString) {
    this.dateOfBirthString = dateOfBirthString;
  }
}