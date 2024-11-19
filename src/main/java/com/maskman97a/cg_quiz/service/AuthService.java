package com.maskman97a.cg_quiz.service;

import com.maskman97a.cg_quiz.dto.enums.RoleTypeEnum;
import com.maskman97a.cg_quiz.dto.enums.MailStatus;
import com.maskman97a.cg_quiz.dto.enums.UserTypeEnum;
import com.maskman97a.cg_quiz.dto.request.RegisterRequest;
import com.maskman97a.cg_quiz.entity.EmailEntity;
import com.maskman97a.cg_quiz.entity.OtpEntity;
import com.maskman97a.cg_quiz.entity.UserEntity;
import com.maskman97a.cg_quiz.exception.ValidateException;
import com.maskman97a.cg_quiz.repository.EmailRepository;
import com.maskman97a.cg_quiz.repository.OtpRepository;
import com.maskman97a.cg_quiz.repository.UserRepository;
import com.maskman97a.cg_quiz.utils.DataUtils;
import com.maskman97a.cg_quiz.utils.OtpUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.usertype.UserType;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService extends BaseService {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final OtpRepository otpRepository;

    @Transactional
    public void register(HttpServletRequest httpServletRequest, RegisterRequest registerRequest) throws ValidateException {
        if (DataUtils.isNullOrEmpty(registerRequest.getEmail())) {
            throw new ValidateException("Email không được để trống");
        }
        if (DataUtils.isNullOrEmpty(registerRequest.getPassword())) {
            throw new ValidateException("Mật khẩu không được để trống");
        }
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmailIgnoreCase(registerRequest.getEmail());
        if (optionalUserEntity.isPresent()) {
            throw new ValidateException("Email đã tồn tại");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setFullName(registerRequest.getFullName());
        userEntity.setEmail(registerRequest.getEmail());
        userEntity.setPassword(registerRequest.getPassword());
        userEntity.setUserType(UserTypeEnum.STUDENT);
        userRepository.save(userEntity);

    }

    public void forgetOtp(String otpKey, String otp, String newPassword) throws ValidateException {
        Optional<OtpEntity> optionalOtpEntity = otpRepository.getOtpByOtpKeyAndFunction(otpKey, "FORGET_PASSWORD");
        if (optionalOtpEntity.isPresent() && optionalOtpEntity.get().getOtp().equals(otp)) {
            Optional<UserEntity> userEntity = userRepository.findById(optionalOtpEntity.get().getReferenceId());
            if (userEntity.isPresent()) {
                userEntity.get().setPassword(newPassword);
                userRepository.save(userEntity.get());
            } else {
                throw new ValidateException("Thông tin user không hợp lệ");
            }
        } else {
            throw new ValidateException("OTP không hợp lệ");
        }
    }

    public String forget(String email) throws IOException, ValidateException {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmailIgnoreCase(email);
        if (optionalUserEntity.isEmpty()) {
            throw new ValidateException("Email không tồn tại trên hệ thống");
        }
        String otp = OtpUtils.generateOtp();
        byte[] mailBody = createMailForgetPasswordOtpContent(otp);

        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setMailBody(mailBody);
        emailEntity.setStatus(MailStatus.NEW);
        emailEntity.setReceiver(email);
        emailEntity.setMailTitle("OTP xác nhận đổi mật khẩu");
        emailRepository.save(emailEntity);

        String otpKey = UUID.randomUUID().toString();
        OtpEntity otpEntity = new OtpEntity();
        otpEntity.setOtpKey(otpKey);
        otpEntity.setFunction("FORGET_PASSWORD");
        otpEntity.setReferenceId(optionalUserEntity.get().getId());
        otpRepository.save(otpEntity);
        return otpKey;
    }

    public byte[] createMailForgetPasswordOtpContent(String otp) throws IOException {
        InputStream input = getClass().getClassLoader().getResourceAsStream("/html/otp-email.html");
        StringBuilder htmlContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            String line;
            while ((line = reader.readLine()) != null) {
                htmlContent.append(line).append("\n");
            }
        }
        input.close();
        String htmlContentStr = htmlContent.toString();
        htmlContentStr = htmlContentStr.replace("{{otp}}", otp);

        return Base64.getEncoder().encode(htmlContentStr.getBytes());
    }
}
