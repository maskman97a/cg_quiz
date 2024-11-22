function isNullOrEmpty(input) {
    return input == null || input === "" || input.length === 0;
}

$(document).ready(function () {
    var today = new Date();
    $('#datePickerInput').datepicker({
        format: 'dd/mm/yyyy', // Customize the date format
        autoclose: true, todayHighlight: true
    }).datepicker('setDate', today);
});

function validatePhoneNumber() {
    let preBookingMsg = $('#pre-booking-message');
    let name = $('#inputName').val();
    if (isNullOrEmpty(name)) {
        preBookingMsg.html('Vui lòng nhập Tên của bạn');
        return false;
    }

    let inputPhoneNumber = $('#inp-phone-number').val();
    if (isNullOrEmpty(inputPhoneNumber)) {
        preBookingMsg.html("Vui lòng nhập số điện thoại");
        return false;
    }

    if (inputPhoneNumber.length !== 10) {
        preBookingMsg.html('Số điện thoại không hợp lệ');
        return false;
    }

}

function onInputPassword(type, id) {
    let validatePasswordMsg = validatePassword(type, $(id));
    $("#input-" + type + "-password-group-icon").show();
    if (isNullOrEmpty(validatePasswordMsg)) {
        $("#icon-valid-" + type + "-password").show();
        $("#icon-invalid-" + type + "-password").hide();
    } else {
        $("#icon-valid-" + type + "-password").hide();
        $("#icon-invalid-" + type + "-password").show();
    }
}

function validatePassword(type, input) {
    if (isNullOrEmpty(input.val())) {
        return input.attr("data-input-name") + " không được để trống.";
    }
    if (input.val().length < 6) {
        return input.attr("data-input-name") + " tối thiểu 6 ký tự.";
    }

    if (type === 'first') {
        if (!isNullOrEmpty($("#inputRePassword").val())) {
            onInputPassword('re-first', 'inputRePassword');
        }
    }
    if (type === 're-first') {
        let pwd = $("#inputPassword").val();
        if (pwd !== input.val()) {
            return "Mật khẩu nhập lại không trùng khớp.";
        }
    }
    if (type === 'new') {
        if (!isNullOrEmpty($("#inputReNewPassword").val())) {
            onInputPassword('re-first', 'inputReNewPassword');
        }
    }
    if (type === 'renew') {
        let pwd = $("#inputNewPassword").val();
        if (pwd !== input.val()) {
            return "Mật khẩu mới nhập lại không trùng khớp.";
        }
    }
}