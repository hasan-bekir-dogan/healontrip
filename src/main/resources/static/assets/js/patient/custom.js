
// preview profile image
$('#profile-settings #user-profile-photo input[type="file"]').on('change', function () {
    previewProfileImage()
});

// update profile
$('#profile-settings').on('submit', function (e) {
    e.preventDefault();

    updateProfile()
})

