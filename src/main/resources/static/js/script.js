function isNullOrEmpty(input) {
    return input == null || input === "" || input.length === 0;
}

$(document).ready(function () {
    var today = new Date();
    $('#datePickerInput').datepicker({
        format: 'dd/mm/yyyy', // Customize the date format
        autoclose: true,
        todayHighlight: true
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