window.generatePaginationNavigation = function (currentPageIndex, totalPages, paginationNavigation, domainModelName) {
    if (currentPageIndex > totalPages)
        window.location = "/" + domainModelName + "/list/" + (totalPages - 1);

    var previousButtons = currentPageIndex <= 1 ?
        "<a style='disabled: true;'>&laquo;</a>"
        :
        "<a href='/" + domainModelName + "/list/" + (currentPageIndex - 2) + "'>&laquo;</a>";

    for (let i = currentPageIndex - 1; i > 0 && i > currentPageIndex - 2 && i < totalPages; i--) {
        previousButtons += "<a href='/" + domainModelName + "/list/" + i + "'>" + i + "</a>";
    }

    var nextButtons = "";
    for (let i = currentPageIndex; i <= currentPageIndex + 1 && i < totalPages; i++) {
        nextButtons += i == currentPageIndex ?
            "<a class='active' href='/" + domainModelName + "/list/" + i + "'>" + i + "</a>"
            :
            "<a href='/" + domainModelName + "/list/" + i + "'>" + i + "</a>";
    }

    if ((currentPageIndex + 2) < totalPages)
        nextButtons += "<a href='/" + domainModelName + "/list/" + (currentPageIndex + 2) + "'>&raquo;</a>";

    paginationNavigation.innerHTML += previousButtons;
    paginationNavigation.innerHTML += nextButtons;
};