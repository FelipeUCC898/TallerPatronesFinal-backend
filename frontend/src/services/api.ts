import axios from 'axios';
import type {
  TShirtCustomizationDto,
  TShirtResponseDto,
  OrderRequestDto,
  OrderResponseDto,
  PaymentRequestDto,
  PaymentResponseDto,
} from '@/types';

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'https://tallerpatronesfinal-backend-production.up.railway.app/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// T-Shirt API
export const tshirtApi = {
  create: async (customization: TShirtCustomizationDto): Promise<TShirtResponseDto> => {
    const response = await api.post<TShirtResponseDto>('/tshirts', customization);
    return response.data;
  },

  getAll: async (): Promise<TShirtResponseDto[]> => {
    const response = await api.get<TShirtResponseDto[]>('/tshirts');
    return response.data;
  },

  getById: async (id: number): Promise<TShirtResponseDto> => {
    const response = await api.get<TShirtResponseDto>(`/tshirts/${id}`);
    return response.data;
  },

  getByCategory: async (category: string): Promise<TShirtResponseDto[]> => {
    const response = await api.get<TShirtResponseDto[]>(`/tshirts/category/${category}`);
    return response.data;
  },

  getBySize: async (size: string): Promise<TShirtResponseDto[]> => {
    const response = await api.get<TShirtResponseDto[]>(`/tshirts/size/${size}`);
    return response.data;
  },

  getByColor: async (color: string): Promise<TShirtResponseDto[]> => {
    const response = await api.get<TShirtResponseDto[]>(`/tshirts/color/${color}`);
    return response.data;
  },
};

// Order API
export const orderApi = {
  create: async (order: OrderRequestDto): Promise<OrderResponseDto> => {
    const response = await api.post<OrderResponseDto>('/orders', order);
    return response.data;
  },

  getAll: async (): Promise<OrderResponseDto[]> => {
    const response = await api.get<OrderResponseDto[]>('/orders');
    return response.data;
  },

  getById: async (id: number): Promise<OrderResponseDto> => {
    const response = await api.get<OrderResponseDto>(`/orders/${id}`);
    return response.data;
  },

  getByCustomerEmail: async (email: string): Promise<OrderResponseDto[]> => {
    const response = await api.get<OrderResponseDto[]>(`/orders/customer/${email}`);
    return response.data;
  },

  updateStatus: async (id: number, status: string): Promise<OrderResponseDto> => {
    const response = await api.put<OrderResponseDto>(`/orders/${id}/status?status=${status}`);
    return response.data;
  },
};

// Payment API
export const paymentApi = {
  process: async (orderId: number, payment: PaymentRequestDto): Promise<PaymentResponseDto> => {
    const response = await api.post<PaymentResponseDto>(`/payments/process/${orderId}`, payment);
    return response.data;
  },

  getStatus: async (paymentId: number): Promise<PaymentResponseDto> => {
    const response = await api.get<PaymentResponseDto>(`/payments/status/${paymentId}`);
    return response.data;
  },

  refund: async (paymentId: number): Promise<PaymentResponseDto> => {
    const response = await api.post<PaymentResponseDto>(`/payments/refund/${paymentId}`);
    return response.data;
  },
};

