// src/api/client.ts
export const API_URL = "http://localhost:8080/api";

interface RequestOptions extends RequestInit {
    body?: any;
    token?: string;
}

export const apiClient = async (endpoint: string, options: RequestOptions = {}) => {
    const { body, token, ...rest } = options;

    const headers: HeadersInit = {
        "Content-Type": "application/json",
        ...(token ? { Authorization: `Bearer ${token}` } : {}),
    };

    const response = await fetch(`${API_URL}${endpoint}`, {
        ...rest,
        headers,
        body: body ? JSON.stringify(body) : undefined,
    });

    const data = await response.json();

    if (!response.ok) {
        throw new Error(data.message || "API request failed");
    }

    return data;
};
