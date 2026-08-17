const activityForm =
    document.getElementById(
        "activityForm"
    );

const planetInput =
    document.getElementById(
        "planet"
    );

const activityNameInput =
    document.getElementById(
        "activityName"
    );

const activityDescriptionInput =
    document.getElementById(
        "activityDescription"
    );

const filterPlanet =
    document.getElementById(
        "filterPlanet"
    );

const activityList =
    document.getElementById(
        "activityList"
    );

const editModal =
    document.getElementById("editModal");

const editActivityForm =
    document.getElementById("editActivityForm");

const editActivityId =
    document.getElementById("editActivityId");

const editPlanet =
    document.getElementById("editPlanet");

const editActivityName =
    document.getElementById("editActivityName");

const editActivityDescription =
    document.getElementById("editActivityDescription");

const closeEditModal =
    document.getElementById("closeEditModal");

const cancelEdit =
    document.getElementById("cancelEdit");

// ========================================
// 頁面載入
// ========================================

loadActivities();


// ========================================
// 新增 Activity
// ========================================

activityForm.addEventListener(
    "submit",
    async event => {

        event.preventDefault();


        const activity = {

            planet:
                planetInput.value,

            name:
                activityNameInput.value,

            description:
                activityDescriptionInput.value

        };


        try {

            const response =
                await fetch(
                    "/api/activities",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body:
                            JSON.stringify(
                                activity
                            )
                    }
                );


            if (!response.ok) {

                throw new Error(
                    "新增 Activity 失敗"
                );
            }


            // 清空表單
            activityForm.reset();


            // 重新載入資料
            loadActivities();

        } catch (error) {

            console.error(error);

            alert(
                "新增 Activity 失敗"
            );
        }
    }
);


// ========================================
// Planet 篩選
// ========================================

filterPlanet.addEventListener(
    "change",
    loadActivities
);


// ========================================
// 載入 Activity
// ========================================

async function loadActivities() {

    try {

        let url =
            "/api/activities";


        // 如果選擇 Planet
        if (filterPlanet.value) {

            url =
                `/api/activities/planet/`
                + filterPlanet.value;
        }


        const response =
            await fetch(url);


        if (!response.ok) {

            throw new Error(
                "無法取得 Activities"
            );
        }


        const activities =
            await response.json();


        displayActivities(
            activities
        );

    } catch (error) {

        console.error(error);

        activityList.innerHTML = `
            <p>
                無法取得 Activities
            </p>
        `;
    }
}


// ========================================
// 顯示 Activity
// ========================================

function displayActivities(
    activities
) {

    activityList.innerHTML =
        "";


    if (
        activities.length === 0
    ) {

        activityList.innerHTML = `
            <p class="activity-empty">
                目前沒有 Activity
            </p>
        `;

        return;
    }


    // -------------------------------
    // 依 Planet 分組
    // -------------------------------

    const groupedActivities =
        groupByPlanet(
            activities
        );


    // -------------------------------
    // 建立 Planet Card
    // -------------------------------

    Object.entries(
        groupedActivities
    ).forEach(
        ([planet, planetActivities]) => {

            const planetCard =
                createPlanetCard(
                    planet,
                    planetActivities
                );

            activityList.appendChild(
                planetCard
            );
        }
    );
}


// ========================================
// 依 Planet 分組
// ========================================

function groupByPlanet(
    activities
) {

    const grouped = {};


    activities.forEach(
        activity => {

            if (!grouped[activity.planet]) {

                grouped[activity.planet] =
                    [];
            }


            grouped[
                activity.planet
            ].push(activity);
        }
    );


    return grouped;
}


// ========================================
// 建立 Planet Card
// ========================================

function createPlanetCard(
    planet,
    activities
) {

    const card =
        document.createElement(
            "div"
        );

    card.className =
        "planet-card";


    const title =
        document.createElement(
            "h3"
        );

    title.className =
        "planet-card-title";

    title.textContent =
        planet;


    const activityGrid =
        document.createElement(
            "div"
        );

    activityGrid.className =
        "planet-activity-grid";


    activities.forEach(
        activity => {

            const activityCard =
                createActivityCard(
                    activity
                );

            activityGrid.appendChild(
                activityCard
            );
        }
    );


    card.appendChild(
        title
    );

    card.appendChild(
        activityGrid
    );


    return card;
}


// ========================================
// 建立 Activity Card
// ========================================

function createActivityCard(
    activity
) {

    const card =
        document.createElement(
            "div"
        );

    card.className =
        "managed-activity-card";


    card.innerHTML = `

        <div class="managed-activity-name">
            ${activity.name}
        </div>

        <div class="managed-activity-description">
            ${activity.description || ""}
        </div>

        <div class="managed-activity-actions">

            <button
                type="button"
                onclick="editActivity(${activity.id})"
            >
                Edit
            </button>

            <button
                type="button"
                onclick="deleteActivity(${activity.id})"
            >
                Delete
            </button>

        </div>

    `;


    return card;
}

// ========================================
// 開啟 Edit Modal
// ========================================

function editActivity(id) {

    fetch(`/api/activities/${id}`)
        .then(response => {

            if (!response.ok) {

                throw new Error(
                    "無法取得 Activity"
                );
            }

            return response.json();
        })
        .then(activity => {

            editActivityId.value =
                activity.id;

            editPlanet.value =
                activity.planet;

            editActivityName.value =
                activity.name;

            editActivityDescription.value =
                activity.description || "";

            editModal.classList.add(
                "show"
            );
        })
        .catch(error => {

            console.error(error);

            alert(
                "無法取得 Activity"
            );
        });
}