
function searchFilter() {
    let gender, specialist, experience, rating;

    $('.search-filter .filter-details #gender input:checked').each(function () {
        if (gender == null)
            gender = $(this).val()
        else
            gender += ',' + $(this).val()
    })
    $('.search-filter .filter-details #speciality input:checked').each(function () {
        if (specialist == null)
            specialist = $(this).val()
        else
            specialist += ',' + $(this).val()
    })
    $('.search-filter .filter-details #experience input:checked').each(function () {
        if (experience == null)
            experience = $(this).val()
        else
            experience += ',' + $(this).val()
    })
    $('.search-filter .filter-details #rating input:checked').each(function () {
        if (rating == null)
            rating = $(this).val()
        else
            rating += ',' + $(this).val()
    })

    let pathVariable = '';

    if (gender != null) {
        if (pathVariable === '')
            pathVariable += 'gen=' + gender;
        else
            pathVariable += '&gen=' + gender;
    }
    if (specialist != null) {
        if (pathVariable === '')
            pathVariable += 'spe=' + specialist;
        else
            pathVariable += '&spe=' + specialist;
    }
    if (experience != null) {
        if (pathVariable === '')
            pathVariable += 'exp=' + experience;
        else
            pathVariable += '&exp=' + experience;
    }
    if (rating != null) {
        if (pathVariable === '')
            pathVariable += 'rat=' + rating;
        else
            pathVariable += '&rat=' + rating;
    }

    let url = '?' + pathVariable;

    window.location.replace(url);
}