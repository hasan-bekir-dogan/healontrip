package com.healontrip.service;

import com.healontrip.dto.*;

public interface EmailService {
    String sendSimpleMail(EmailDetailsDto details);
}
