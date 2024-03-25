
function clearItems() {
    $('#change-password .form-group input').val('')
}

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

function hideChangePasswordSuccessMessage() {
    $(`#change-password-success`).remove()
}

function showChangePasswordSuccessMessage() {
    let message = ` <div id="change-password-success" class="alert alert-success alert-dismissible fade show" role="alert">
                        Password has been changed successfully!
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>`;

    $(message).insertBefore($('#change-password'))
}

function hideChangePasswordErrors() {
    $(`#change-password .form-group`).removeClass('form-error')
    $(`#change-password .form-group .text-danger`).remove()
}

function showChangePasswordErrors(field, message) {
    $(`#change-password #${field}Area`).addClass('form-error')
    $(`#change-password #${field}Area`).append(`<p class="text-danger">${message}</p>`)
}

function getChangePasswordData() {
    // define query selector
    let oldPasswordSelector = '#change-password #oldPasswordArea input'
    let newPasswordSelector = '#change-password #newPasswordArea input'
    let confirmPasswordSelector = '#change-password #confirmPasswordArea input'

    // disable input and button
    let affectedItemList = []

    affectedItemList.push(oldPasswordSelector)
    affectedItemList.push(newPasswordSelector)
    affectedItemList.push(confirmPasswordSelector)
    affectedItemList.push('#change-password .submit-section button[type="submit"]')

    disableItems(affectedItemList)


    // get data
    let oldPassword = $(oldPasswordSelector).val()
    let newPassword = $(newPasswordSelector).val()
    let confirmPassword = $(confirmPasswordSelector).val()


    // ********** form data (begin) ********** //
    let formData = new FormData();

    // profile photo
    formData.append("oldPassword", oldPassword);
    formData.append("newPassword", newPassword);
    formData.append("confirmPassword", confirmPassword);
    // ********** form data (end) ********** //


    return {
        affectedItemList,
        formData
    };
}

async function changePassword() {
    //jQuery
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    let resultObject = getChangePasswordData();
    let formData = resultObject.formData;
    let affectedItemList = resultObject.affectedItemList;

    let response = await fetch($('#change-password').attr('action'), {
        headers: {
            'X-CSRF-Token': token,
            'X-CSRF-HEADER': header
        },
        method: $('#change-password').attr('method'),
        enctype: 'multipart/form-data',
        body: formData
    });

    let res = await response.json()

    if (res.status === 'success') { // success
        // hide change password success message
        hideChangePasswordSuccessMessage()

        // hide errors
        hideChangePasswordErrors()

        // show change password success message
        showChangePasswordSuccessMessage()

        // clear items
        clearItems()

        // enable items
        enableItems(affectedItemList)
    } else { // error
        // hide change password success message
        hideChangePasswordSuccessMessage()

        // hide errors
        hideChangePasswordErrors()

        // show errors
        for (let j = 0; j < res.errors.length; j++)
            showChangePasswordErrors(res.errors[j].field, res.errors[j].defaultMessage)

        let formErrorArea = `#change-password`;
        let headerHeight = $('.header').height()
        let position = $(formErrorArea).offset().top - headerHeight;
        $("html, body").animate({ scrollTop: position }, "slow");

        // enable items
        enableItems(affectedItemList)
    }
}
