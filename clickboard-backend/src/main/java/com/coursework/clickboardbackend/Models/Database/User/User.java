package com.coursework.clickboardbackend.Models.Database.User;

import com.coursework.clickboardbackend.Models.DTO.User.SignUpDTO;
import com.coursework.clickboardbackend.Models.Database.ShoppingCart.ShoppingCart;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "vk_id", unique = true)
    private Integer vkId;

    @Column(name = "firstname")
    @NotNull
    private String firstName;

    @Column(name = "lastname")
    @NotNull
    private String lastName;

    @Column(name = "patronymic")
    @NotNull
    private String patronymic;

    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "email")
    @NotNull
    private String email;

    @Column(name = "isTwoFactorEnabled")
    @NotNull
    private boolean isTwoFactorEnabled = true;

    @Column(name = "confirmationCode")
    private String confirmationCode;

    @Column(name = "confirmationCodeExpiration")
    private LocalDateTime confirmationCodeExpiration;

    @Column(name = "deposit")
    @NotNull
    private float deposit = 0.f;

    @Column(name = "isChildModeEnabled")
    @NotNull
    private boolean isChildModeEnabled = false;

    @Column(name = "areNotificationsEnabled")
    @NotNull
    private boolean areNotificationsEnabled = true;

    @JoinColumn(name = "cart_id")
    @OneToOne(cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart = new ShoppingCart();

    public User(){

    }


    public User (SignUpDTO signUpDTO) {
        this.firstName = signUpDTO.getFirstName();
        this.lastName = signUpDTO.getLastName();
        this.patronymic = signUpDTO.getPatronymic();
        this.username = signUpDTO.getUsername();
        this.password = signUpDTO.getPassword();
        this.email = signUpDTO.getEmail();
    }

    public void update(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.patronymic = user.getPatronymic();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.deposit = user.getDeposit();
        this.isTwoFactorEnabled = user.isTwoFactorEnabled();
        this.confirmationCode = user.getConfirmationCode();
        this.confirmationCodeExpiration = user.getConfirmationCodeExpiration();
        this.isChildModeEnabled = user.isChildModeEnabled();
        this.areNotificationsEnabled = user.isAreNotificationsEnabled();
        this.shoppingCart = user.getShoppingCart();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getVkId() {
        return vkId;
    }

    public void setVkId(Integer vkId) {
        this.vkId = vkId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isTwoFactorEnabled() {
        return isTwoFactorEnabled;
    }

    public void setTwoFactorEnabled(boolean twoFactorEnabled) {
        isTwoFactorEnabled = twoFactorEnabled;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public LocalDateTime getConfirmationCodeExpiration() {
        return confirmationCodeExpiration;
    }

    public void setConfirmationCodeExpiration(LocalDateTime confirmationCodeExpiration) {
        this.confirmationCodeExpiration = confirmationCodeExpiration;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public boolean isChildModeEnabled() {
        return isChildModeEnabled;
    }

    public void setChildModeEnabled(boolean childModeEnabled) {
        isChildModeEnabled = childModeEnabled;
    }

    public boolean isAreNotificationsEnabled() {
        return areNotificationsEnabled;
    }

    public void setAreNotificationsEnabled(boolean areNotificationsEnabled) {
        this.areNotificationsEnabled = areNotificationsEnabled;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}