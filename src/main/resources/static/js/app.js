const dateInput =
    document.getElementById("date");

const searchButton =
    document.getElementById("searchButton");

const errorMessage =
    document.getElementById("errorMessage");

const sunInfo =
    document.getElementById("sunInfo");

const hoursContainer =
    document.getElementById("hoursContainer");


// ========================================
// 頁面載入時設定今天
// ========================================

const today =
    new Date()
        .toISOString()
        .split("T")[0];

dateInput.value = today;


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
    // 基本驗證
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

    // -------------------------------
    // 基本資訊
    // -------------------------------

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


    // -------------------------------
    // 清除舊資料
    // -------------------------------

    hoursContainer.innerHTML =
        "";


    // -------------------------------
    // 建立 24 個 Planetary Hours
    // -------------------------------

    data.hours.forEach(
        hour => {

            const card =
                document.createElement(
                    "div"
                );

            card.className =
                "hour-card";


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
                    ${hour.start}
                    -
                    ${hour.end}
                </div>
            `;


            hoursContainer.appendChild(
                card
            );
        }
    );
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