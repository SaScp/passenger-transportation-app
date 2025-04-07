import axios from "axios";
const api = axios.create({
    baseURL: "http://localhost:8080/api/v1",
});

export const findRoutesByParams = async (params) => {
    return await api.get("/route/find", { params });
};

export const getAllRoutes = async (params) => {
    return await api.get("/route/find-all", { params });
};

export const getBookingsByPhone = async (phone) => {
    return await api.get("/booking/find-by-phone", {
        params: { phone: phone },
    });
};
export const findById = async (routeIds) => {
   return  api.get(
        `/graph/find-by-ids?id=${routeIds}`
    );
}
export const getAllTypes = async () => {
    return api.get(
        "/route/find-types"
    );
};
export const getAllGraphs = async () => {
    return api.get(
        "/graph/find-all-graph"
    );
};
export const getRoutesByDepId = async (params) =>{
    return api.get(
        "/route/find-routes-by-dep-id",
        {params}
    );
}
export const revokeBooking = async (booking_id) => {
    return await api.delete("/booking/revoke", { params: { booking_id } });
};

export const createBooking = async (data) => {
    return await api.post("/booking/create", data);
};

export const getRouteById = async (route_id) => {
    return await api.get("/route/find-by-id", {
        params: { route_id: route_id },
    });
};

export const getTransportTypes = async () => {
    return await api.get("/route/find-types");
};