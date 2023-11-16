package com.healontrip.service.impl;

import com.healontrip.service.*;
import org.springframework.stereotype.Service;



@Service
public class ValidationServiceImpl implements ValidationService {
    @Override
    public boolean validEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    @Override
    public boolean validUsername(String userName) {
        //return userName.matches("^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$");
        return userName.matches("^[a-zA-Z0-9-]{5,29}$");
    }

    @Override
    public boolean validPassword(String password) {
        return password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");
    }
}
