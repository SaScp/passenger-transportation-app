import axios from "axios";
const api = axios.create({
    baseURL: "http://localhost:9000/dev/api/v1/booking",
});

export const findRoutesByParams = async (params) => {
    return await api.get("/find", { params });
};

export const getAllRoutes = async (params) => {
    return await api.get("/find-all", { params });
};

export const getBookingsByPhone = async (phone) => {
    return await api.get("/find-by-phone", {
        params: { phone: phone },
    });
};
export const findById = async (routeIds) => {
   return  api.get(
        `/find-by-ids?id=${routeIds}`
    );
}
export const getAllTypes = async () => {
    return api.get(
        "/find-types"
    );
};
export const getAllGraphs = async () => {
    return api.get(
        "/find-all-graph"
    );
};
export const getRoutesByDepId = async (params) =>{
    return api.get(
        "/find-routes-by-dep-id",
        {params}
    );
}
export const revokeBooking = async (booking_id) => {
    return await api.delete("/revoke", { params: { booking_id } });
};

export const createBooking = async (data) => {
    return await api.post("/create", data);
};

export const getRouteById = async (route_id) => {
    return await api.get("/find-by-id", {
        params: { route_id: route_id },
    });
};

export const getTransportTypes = async () => {
    return await api.get("/find-types");
};