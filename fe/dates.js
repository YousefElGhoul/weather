function updateDates() {
    const day0Element = document.getElementById("date0");
    const day1Element = document.getElementById("date1");
    const day2Element = document.getElementById("date2");
    const day3Element = document.getElementById("date3");
    const day4Element = document.getElementById("date4");
    const day5Element = document.getElementById("date5");
    const day6Element = document.getElementById("date6");

    const elements = [
        day0Element,
        day1Element,
        day2Element,
        day3Element,
        day4Element,
        day5Element,
        day6Element
    ];

    const weekdays = ["Sun.", "Mon.", "Tue.", "Wed.", "Thu.", "Fri.", "Sat."];
    const todayDate = new Date();

    for (let i = 0; i < 7; i++) {
        const currentDate = new Date(todayDate);
        currentDate.setDate(todayDate.getDate() + i);

        const weekday = weekdays[currentDate.getDay()];
        const day = currentDate.getDate();
        const month = currentDate.getMonth() + 1;
        const year = currentDate.getFullYear();

        elements[i].textContent = `${weekday} ${day}/${month}/${year}`;
    }
}