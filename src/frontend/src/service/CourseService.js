import http from "@/service/http-common";

export const fetchCourse = (id) => {
    return http.get("/courses/" + id)
}

export const fetchCourses = () => {
    return http.get("/courses");
}