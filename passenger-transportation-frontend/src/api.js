import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080/api/v1/booking",
});

export const findRoutesByParams = async (params) => {
    return await api.get("/find", { params });
};

export const getAllRoutes = async (params) => {
    const response = await api.get("/find-all", { params });
    return response;
};

export const getBookingsByPhone = async (phone) => {
    const response = await api.get("/find-by-phone", {
        params: { phone },
    });
    return response.data;
};

export const revokeBooking = async (booking_id) => {
    await api.delete("/revoke", { params: { booking_id } });
};

export const createBooking = async (data) => {
    await api.post("/create", data);
};

export const getRouteById = async (route_id) => {
    const response = await api.get("/find-by-id", {
        params: { route_id },
    });
    return response.data[0];
};

export const getTransportTypes = async () => {
    const response = await api.get("/find-types");
    return response.data;
};