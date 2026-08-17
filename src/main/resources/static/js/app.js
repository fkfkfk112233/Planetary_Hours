const dateInput =
    document.getElementById("date");

const searchButton =
    document.getElementById("searchButton");

const errorMessage =
    document.getElementById("errorMessage");

const sunInfo =
    document.getElementById("sunInfo");

const dayHoursContainer =
    document.getElementById(
        "dayHoursContainer"
    );

const nightHoursContainer =
    document.getElementById(
        "nightHoursContainer"
    );

const currentHour =
    document.getElementById(
        "currentHour"
    );

const currentActivities =
    document.getElementById(
        "currentActivities"
    );


// ========================================
// 儲存目前查詢結果
// ========================================

let currentData = null;


// ========================================
// 記錄目前顯示的 Planet
// ========================================

let currentPlanet = null;


// ========================================
// 取得台灣今天日期
// ========================================

function getTaiwanDate() {

    return new Intl.DateTimeFormat(
        "en-CA",
        {
            timeZone: "Asia/Taipei",
            year: "numeric",
            month: "2-digit",
            day: "2-digit"
        }
    ).format(
        new Date()
    );
}


// ========================================
// 取得台灣目前時間 HH:mm:ss
// ========================================

function getTaiwanTime() {

    return new Intl.DateTimeFormat(
        "en-GB",
        {
            timeZone: "Asia/Taipei",
            hour: "2-digit",
            minute: "2-digit",
            second: "2-digit",
            hour12: false
        }
    ).format(
        new Date()
    );
}


// ========================================
// 頁面載入時設定今天
// ========================================

const today =
    getTaiwanDate();

dateInput.value =
    today;


// ========================================
// 每秒更新 Current Planetary Hour
// ========================================

setInterval(
    updateCurrentPlanetaryHour,
    1000
);


// ========================================
// 查詢按鈕
// ========================================

searchButton.addEventListener(
    "click",
    calculatePlanetaryHours
);


// ========================================
// 查詢 Planetary Hours
// ========================================

async function calculatePlanetaryHours() {

    clearError();

    const date =
        dateInput.value;

    const location =
        document.querySelector(
            'input[name="location"]:checked'
        ).value;


    // -------------------------------
    // 日期驗證
    // -------------------------------

    if (!date) {

        showError(
            "請選擇日期"
        );

        return;
    }


    // -------------------------------
    // 建立 API URL
    // -------------------------------

    const url =
        `/api/planetary-hours`
        + `?date=${date}`
        + `&location=${location}`;


    try {

        const response =
            await fetch(url);


        // ---------------------------
        // HTTP 錯誤
        // ---------------------------

        if (!response.ok) {

            const error =
                await response.json();

            showError(
                error.message
            );

            return;
        }


        // ---------------------------
        // 取得 JSON
        // ---------------------------

        const data =
            await response.json();


        // ---------------------------
        // 顯示結果
        // ---------------------------

        displayResult(data);

    } catch (error) {

        showError(
            "無法連線到伺服器"
        );

        console.error(error);
    }
}


// ========================================
// 顯示結果
// ========================================

function displayResult(data) {

    currentData =
        data;

    // 每次重新查詢時清除目前 Planet
    currentPlanet =
        null;


    document.getElementById(
        "resultDate"
    ).textContent =
        data.date;


    document.getElementById(
        "resultLocation"
    ).textContent =
        data.location;


    document.getElementById(
        "resultSunrise"
    ).textContent =
        data.sunrise;


    document.getElementById(
        "resultSunset"
    ).textContent =
        data.sunset;


    sunInfo.style.display =
        "grid";


    dayHoursContainer.innerHTML =
        "";

    nightHoursContainer.innerHTML =
        "";


    data.hours.forEach(
        hour => {

            const card =
                createHourCard(hour);

            if (hour.type === "DAY") {

                dayHoursContainer.appendChild(
                    card
                );

            } else {

                nightHoursContainer.appendChild(
                    card
                );
            }
        }
    );


    // 找目前 Planetary Hour
    updateCurrentPlanetaryHour();
}


// ========================================
// 建立 Hour Card
// ========================================

function createHourCard(hour) {

    const card =
        document.createElement(
            "div"
        );

    card.className =
        "hour-card";

    card.dataset.hour =
        hour.hour;

    card.innerHTML = `
        <div class="hour-number">
            Hour ${hour.hour}
        </div>

        <div class="hour-type">
            ${hour.type}
        </div>

        <div class="planet">
            ${hour.planet}
        </div>

        <div class="time">
            ${formatTime(hour.start)}
            -
            ${formatTime(hour.end)}
        </div>
    `;

    return card;
}


// ========================================
// 時間格式化
// ========================================

function formatTime(time) {

    return time.substring(
        0,
        5
    );
}


// ========================================
// 更新目前 Planetary Hour
// ========================================

async function updateCurrentPlanetaryHour() {

    // 尚未查詢資料
    if (!currentData) {

        return;
    }


    const today =
        getTaiwanDate();


    // 只有查詢今天才顯示
    if (currentData.date !== today) {

        currentHour.innerHTML =
            "";

        currentActivities.innerHTML =
            "";

        currentPlanet =
            null;

        return;
    }


    const currentTime =
        getTaiwanTime();


    const hour =
        currentData.hours.find(
            item =>
                isTimeInRange(
                    currentTime,
                    item.start,
                    item.end
                )
        );


    if (!hour) {

        currentHour.innerHTML =
            "";

        currentActivities.innerHTML =
            "";

        currentPlanet =
            null;

        return;
    }


    // -------------------------------
    // 顯示目前時間 + Planetary Hour
    // -------------------------------

    currentHour.innerHTML = `
        <div class="current-time">
            ${currentTime}
        </div>

        <div class="current-planet">
            Hour ${hour.hour}
            -
            ${hour.planet}
        </div>
    `;


    // -------------------------------
    // Highlight 目前 Hour
    // -------------------------------

    // 先清除之前的 Highlight
    document.querySelectorAll(
        ".hour-card.current"
    ).forEach(
        card => {
            card.classList.remove(
                "current"
            );
        }
    );


    highlightCurrentHour(
        hour.hour
    );


    // -------------------------------
    // Planet 改變才重新查 Activity
    // -------------------------------

    if (
        currentPlanet !==
        hour.planet
    ) {

        currentPlanet =
            hour.planet;

        await loadCurrentActivities(
            hour.planet
        );
    }
}


// ========================================
// 查詢目前 Planet 的 Activities
// ========================================

async function loadCurrentActivities(
    planet
) {

    currentActivities.innerHTML = `
        <div class="activities-title">
            Activities
        </div>

        <div class="activities-loading">
            Loading...
        </div>
    `;


    try {

        const response =
            await fetch(
                `/api/activities/planet/${planet}`
            );


        if (!response.ok) {

            throw new Error(
                "無法取得 Activities"
            );
        }


        const activities =
            await response.json();


        displayCurrentActivities(
            activities
        );

    } catch (error) {

        console.error(error);

        currentActivities.innerHTML = `
            <div class="activities-title">
                Activities
            </div>

            <div class="activities-error">
                無法取得 Activities
            </div>
        `;
    }
}


// ========================================
// 顯示目前 Planet 的 Activities
// ========================================

function displayCurrentActivities(
    activities
) {

    if (
        activities.length === 0
    ) {

        currentActivities.innerHTML = `
            <div class="activities-title">
                Activities
            </div>

            <div class="activities-empty">
                目前沒有可用的 Activity
            </div>
        `;

        return;
    }


    const activityItems =
        activities
            .map(
                activity => `
                    <div class="activity-item">
                        ${activity.name}
                    </div>
                `
            )
            .join("");


    currentActivities.innerHTML = `
        <div class="activities-title">
            Activities
        </div>

        <div class="activity-list">
            ${activityItems}
        </div>
    `;
}


// ========================================
// 判斷時間是否在區間
// ========================================

function isTimeInRange(
    current,
    start,
    end
) {

    // 一般情況，例如 10:00 → 11:00
    if (start < end) {

        return (
            current >= start &&
            current < end
        );
    }


    // 跨午夜，例如 23:30 → 00:30
    return (
        current >= start ||
        current < end
    );
}


// ========================================
// Highlight 目前 Hour
// ========================================

function highlightCurrentHour(
    hourNumber
) {

    const card =
        document.querySelector(
            `[data-hour="${hourNumber}"]`
        );

    if (card) {

        card.classList.add(
            "current"
        );
    }
}


// ========================================
// 顯示錯誤
// ========================================

function showError(message) {

    errorMessage.textContent =
        message;

    errorMessage.style.display =
        "block";
}


// ========================================
// 清除錯誤
// ========================================

function clearError() {

    errorMessage.textContent =
        "";

    errorMessage.style.display =
        "none";
}