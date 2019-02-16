const API_URL = "http://138.68.75.157/teacheritmo/api/rest/";

/**
 * Склепано на скорую руку и без души, 0 вопросов
 */

const util = {
    isEmpty: (text) => {
        return text === undefined || text == null || text === "";
    },
    isNotEmpty: (text) => {
        return !util.isEmpty(text);
    },
    getUrlVars: () => {
        let vars = [], hash;
        let hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
        for (let i = 0; i < hashes.length; i++) {
            hash = hashes[i].split('=');
            vars.push(hash[0]);
            vars[hash[0]] = hash[1];
        }
        return vars;
    },
    isInt: (n) => {
        return Number(n) === n && n % 1 === 0;
    },
    isFloat: (n) => {
        return Number(n) === n && n % 1 !== 0;
    }
};

const rest = {
    get: (target, callback) => {
        $.ajax({
            type: "GET",
            url: API_URL + target,
            dataType: "json",
            headers: {
                Accept: "application/json;charset=UTF-8"
            }
        }).then((data, textStatus, xhr) => {
            callback(xhr.status, data);
        }, (xhr) => {
            callback(xhr.status, xhr.responseJSON);
        });
    },
    post: (target, data, callback) => {
        $.ajax({
            type: "POST",
            url: API_URL + target,
            data: JSON.stringify(data),
            contentType: "application/json;charset=UTF-8",
            headers: {
                Accept: "application/json;charset=UTF-8"
            },
            dataType: "json"
        }).then((data, textStatus, xhr) => {
            callback(xhr.status, data);
        }, (req) => {
            callback(req.status, req.responseJSON);
        });
    }
};

const notification = {
    show: (text) => {
        window.alert(text);
    },
    error: (text, status, data) => {
        let message = text;
        if (data.status !== undefined) {
            message += "\nСтатус: " + data.status;
            if (util.isNotEmpty(data.description)) {
                message += "\nОписание: " + data.description;
            }
        } else if (status > 0) {
            message += "\nСтатус: " + status;
        }
        if (util.isNotEmpty(data.reason)) {
            message += "\nПричина: " + data.reason;
        }
        notification.show(message);
    }
};

const teachers = {
    searchTimer: undefined, 
    init: () => {
        $("#create").click(() => {
            window.open("teacher.html", "_self");
        });
        $("#search-input").keyup(() => {
            let teacher = $("#search-input").val();
            if (teachers.searchTimer !== undefined) {
                clearTimeout(teachers.searchTimer);
                teachers.searchTimer = undefined;
            }
            if (util.isEmpty(teacher) || teacher.length < 3) {
                teachers.fill(null);
                return;
            }
            teachers.searchTimer = setTimeout(() => {
                teachers.load(teacher);
            }, 300);
        });
    },
    load: (teacher) => {
        rest.get("teachers/" + teacher, (status, data) => { 
            if (status === 200) {
                teachers.fill(data);
                return;
            }
            notification.error("Не удалось загрузить список преподавателей", status, data);
        });
    },
    fill: (data) => {
        let container = $("#teachers");
        container.empty();
        if (data === undefined || data == null) {
            return;
        }
        let builder = "";
        for (let i = 0; i < data.size; i++) {
            let teacher = data.teachers[i];
            builder += "<a class='item-teacher cl' tabindex='" + (i + 2) + "' href='reviews.html?id=" + teacher.id + "'>";
            builder += "<div>";
            builder += "<span class='name'>" + teacher.name + " (" + teacher.id + ")</span>";
            builder += "<span class='post'>Должность: " + teacher.post + "</span>";
            builder += "</div><div><i class='fas fa-angle-right'></i></div></a>";
        }
        container.html(builder);
    }
};

const teacher = {
    init: () => {
        $("#back").click(() => {
            window.open("index.html", "_self");
        });
        $("#add").click(() => {
            let name = $("#name").val();
            let post = $("#post").val();
            let pid = parseInt($("#pid").val());
            if (util.isEmpty(name) || util.isEmpty(post) || !util.isInt(pid)) {
                notification.show("Заполните поля");
                return;
            }
            teacher.add(name, post, pid);
        });
    },
    add: (name, post, pid) => {
        let data = {
            "id": pid,
            "name": name,
            "post": post
        };
        rest.post("teacher/create", data, (status, data) => {
            if (status === 201) {
                window.open("index.html", "_self");
                return;
            }
            notification.error("Не удалось создать преподавателя", status, data);
        });
    }
};

const reviews = {
    pid: NaN,
    name: undefined,
    init: () => {
        let query = util.getUrlVars();
        reviews.pid = parseInt(query["id"]);
        if (!util.isInt(reviews.pid)) {
            window.open("index.html", "_self");
            return;
        }
        $("#back").click(() => {
            window.open("index.html", "_self");
        });
        $("#create").click(() => {
            let url = "review.html?id=" + reviews.pid;
            if (util.isNotEmpty(reviews.name)) {
                url += "&name=" + reviews.name;
            }
            window.open(url, "_self");
        });
        $("#teacher").html(reviews.pid);
        reviews.load(reviews.pid);
    },
    load: (pid) => {
        rest.get("reviews/" + pid, (status, data) => { 
            if (status === 200) {
                reviews.fill(data);
                return;
            }
            notification.error("Не удалось загрузить отзывы", status, data);
        });
    },
    fill: (data) => {
        reviews.pid = data.teacher.id;
        reviews.name = data.teacher.name;
        $("#teacher").html(data.teacher.name + " (" + data.teacher.id + ")");
        $("#criteria1val").html(reviews.parse(data.criteria1));
        $("#criteria1progress").attr("style", "width: " + (parseFloat(data.criteria1) * 20) + "%");
        $("#criteria2val").html(reviews.parse(data.criteria2));
        $("#criteria2progress").attr("style", "width: " + (parseFloat(data.criteria2) * 20) + "%");
        $("#criteria3val").html(reviews.parse(data.criteria3));
        $("#criteria3progress").attr("style", "width: " + (parseFloat(data.criteria3) * 20) + "%");
        $("#criteria4val").html(reviews.parse(data.criteria4));
        $("#criteria4progress").attr("style", "width: " + (parseFloat(data.criteria4) * 20) + "%");
        $("#criteria5val").html(reviews.parse(data.criteria5));
        $("#criteria5progress").attr("style", "width: " + (parseFloat(data.criteria5) * 20) + "%");
        let comments = "";
        for (let i = 0; i < data.commentsSize; i++) {
            let comment = data.comments[i];
            comments += "<div class='comment m-b-10'>" + comment + "</div>";
        }
        $("#comments").html(comments);
        $("#review-count").html(util.isInt(data.total) ? data.total : 0);
    },
    parse: (number) => {
        let fl = parseFloat(number);
        return util.isFloat(fl) ? fl.toFixed(2) : util.isInt(number) ? number : 0;
    }
};

const review = {
    init: () => {
        let query = util.getUrlVars();
        let pid = parseInt(query["id"]);
        let name = decodeURI(query["name"]);
        if (!util.isInt(pid)) {
            window.open("index.html", "_self");
            return;
        }
        $("#back").click(() => {
            window.open("reviews.html?id=" + pid, "_self");
        });
        $("#teacher").html(name + " (" + pid + ")");
        $(".star").hover(function() {
            let star = parseInt($(this).attr("star"));
            let parent = $(this).parent();
            for (let i = 0; i < 6; i++) {
                parent.removeClass("stars-" + i);
            }
            parent.addClass("stars-" + star);
            parent.attr("star", star);
        });
        $("#create").click(() => {
            review.create(pid);
        });
        $("#comment").keydown(function(e) {
            if (e.ctrlKey && e.keyCode === 13) {
                review.create(pid);
            }
        });
    },
    create: (pid) => {
        let criteria1 = parseInt($("#criteria1").attr("star"));
        let criteria2 = parseInt($("#criteria2").attr("star"));
        let criteria3 = parseInt($("#criteria3").attr("star"));
        let criteria4 = parseInt($("#criteria4").attr("star"));
        let criteria5 = parseInt($("#criteria5").attr("star"));
        let comment = $("#comment").val();
        if (util.isEmpty(comment)) {
            comment = null;
        }
        if (review.validate(criteria1) && review.validate(criteria2) && 
            review.validate(criteria3) && review.validate(criteria4) && review.validate(criteria5)) {
            review.add(pid, comment, criteria1, criteria2, criteria3, criteria4, criteria5);
            return;
        }
        notification.show("Заполните поля");
    },
    validate: (number) => {
        return util.isInt(number) && number > 0 && number < 6;
    },
    add: (pid, comment, criteria1, criteria2, criteria3, criteria4, criteria5) => {
        let data = {
            "teacherId": pid,
            "comment": comment,
            "criteria1": criteria1,
            "criteria2": criteria2,
            "criteria3": criteria3,
            "criteria4": criteria4,
            "criteria5": criteria5
        };
        rest.post("review/create", data, (status, data) => {
            if (status === 201) {
                window.open("reviews.html?id=" + pid, "_self");
                return;
            }
            notification.error("Не удалось создать отзыв", status, data);
        });
    }
};