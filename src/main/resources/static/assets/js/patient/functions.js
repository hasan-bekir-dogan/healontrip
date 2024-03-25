
function disableItems(list) {
    for (let i = 0; i < list.length; i++) {
        $(list[i]).prop('disabled', true)
    }
}

function enableItems(list) {
    for (let i = 0; i < list.length; i++) {
        $(list[i]).prop('disabled', false)
    }
}

function hideErrors() {
    hideSuccessMessage()

    let jQuerySelector = `#profile-settings .form-error`

    // remove error
    $(jQuerySelector).removeClass('form-error')
    $(`#profile-settings .text-danger`).remove()
}

function showErrors(field, message) {
    let jQuerySelector = `#profile-settings #user-${field}`

    // show error
    $(jQuerySelector).addClass('form-error')
    $(jQuerySelector).append(`<p class="text-danger error-user-${field}">${message}</p>`)
}

function hideSuccessMessage() {
    $('#profile-settings #profile-success-message').remove()
}

function showSuccessMessage() {
    hideSuccessMessage();

    let message = ` <div id="profile-success-message" class="alert alert-success alert-dismissible fade show" role="alert">
                        You have successfully updated profile!
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>`;

    $(message).insertBefore($('#profile-settings #profile-information'));

    let formSuccessArea = `#profile-settings #profile-success-message`;
    let headerHeight = $('.header').height()
    let position = $(formSuccessArea).offset().top - headerHeight;
    $("html, body").animate({ scrollTop: position }, "slow");
}

function getProfileData() {
    // define query selector
    let userNameSelector = '#profile-settings #user-userName input[type="text"]'
    let firstNameSelector = '#profile-settings #user-firstName input[type="text"]'
    let lastNameSelector = '#profile-settings #user-lastName input[type="text"]'
    let phoneSelector = '#profile-settings #user-phone-number input[type="number"]'
    let genderSelector = '#profile-settings #user-gender #gender'
    let birthDateSelector = '#profile-settings #user-birth-date input[type="date"]'
    let contactCitySelector = '#profile-settings #user-contact-city input[type="text"]'
    let contactStateSelector = '#profile-settings #user-contact-state input[type="text"]'
    let contactCountrySelector = '#profile-settings #user-contact-country input[type="text"]'
    let contactPostalCodeSelector = '#profile-settings #user-contact-postal-code input[type="text"]'
    let contactAddressLineSelector = '#profile-settings #user-contact-address-line input[type="text"]'


    // disable input and button
    let affectedItemList = []

    affectedItemList.push(userNameSelector)
    affectedItemList.push(firstNameSelector)
    affectedItemList.push(lastNameSelector)
    affectedItemList.push(phoneSelector)
    affectedItemList.push(genderSelector)
    affectedItemList.push(birthDateSelector)
    affectedItemList.push(contactCitySelector)
    affectedItemList.push(contactStateSelector)
    affectedItemList.push(contactCountrySelector)
    affectedItemList.push(contactPostalCodeSelector)
    affectedItemList.push(contactAddressLineSelector)
    affectedItemList.push('#profile-settings .submit-section button[name="form_submit"]')

    disableItems(affectedItemList)


    // get data
    let userName = $(userNameSelector).val()
    let firstName = $(firstNameSelector).val()
    let lastName = $(lastNameSelector).val()
    let phone = $(phoneSelector).val()
    let gender = $(genderSelector).val()
    let birthDate = $(birthDateSelector).val()
    let contactCity = $(contactCitySelector).val()
    let contactState = $(contactStateSelector).val()
    let contactCountry = $(contactCountrySelector).val()
    let contactPostalCode = $(contactPostalCodeSelector).val()
    let contactAddressLine = $(contactAddressLineSelector).val()


    // ********** form data (begin) ********** //
    let formData = new FormData();

    formData.append("userName", userName);
    formData.append("firstName", firstName);
    formData.append("lastName", lastName);
    formData.append("phone", phone);
    formData.append("gender", gender);
    formData.append("dateOfBirth", birthDate);
    formData.append("city", contactCity);
    formData.append("state", contactState);
    formData.append("country", contactCountry);
    formData.append("postalCode", contactPostalCode);
    formData.append("addressLine", contactAddressLine);
    // ********** form data (end) ********** //


    return {
        affectedItemList,
        formData
    };
}

async function updateProfile() {
    //jQuery
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    let resultObject = getProfileData();
    let formData = resultObject.formData;
    let affectedItemList = resultObject.affectedItemList;

    let response = await fetch($('#profile-settings').attr('action'), {
        headers: {
            'X-CSRF-Token': token,
            'X-CSRF-HEADER': header
        },
        method: $('#profile-settings').attr('method'),
        enctype: 'multipart/form-data',
        body: formData
    });

    let res = await response.json()

    if (res.status === 'success') { // success
        // enable items
        enableItems(affectedItemList)

        // hide errors
        hideErrors()

        // show success message
        showSuccessMessage()
    }
    else { // error
        // hide errors
        hideErrors()

        // show errors
        if (res.errors != null) {
            for (let j = 0; j < res.errors.length; j++) {

                showErrors(
                    res.errors[j].field,
                    res.errors[j].defaultMessage
                )
            }

        }

        let formErrorArea = `#profile-settings .form-error`;
        let headerHeight = $('.header').height()
        let position = $(formErrorArea).offset().top - headerHeight;
        $("html, body").animate({ scrollTop: position }, "slow");

        enableItems(affectedItemList)
    }
}


