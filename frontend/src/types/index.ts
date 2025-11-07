// T-Shirt Types
export interface TShirtCustomizationDto {
  size: string;
  color: string;
  print: string;
  fabric: string;
  price: number;
  sku: string;
  category: 'casual' | 'sport' | 'premium';
}

export interface TShirtResponseDto {
  id: number;
  size: string;
  color: string;
  print: string;
  fabric: string;
  price: number;
  sku: string;
  category: string;
}

// Order Types
export interface OrderItemDto {
  tshirtId: number;
  quantity: number;
}

export interface OrderRequestDto {
  items: OrderItemDto[];
  customerName: string;
  customerEmail: string;
}

export interface OrderItemResponseDto {
  id: number;
  tshirtId: number;
  quantity: number;
  subtotal: number;
  tshirt?: TShirtResponseDto;
}

export interface OrderResponseDto {
  id: number;
  customerName: string;
  customerEmail: string;
  totalAmount: number;
  status: 'PENDING' | 'CONFIRMED' | 'SHIPPED' | 'DELIVERED' | 'CANCELLED';
  createdAt: string;
  items: OrderItemResponseDto[];
}

// Payment Types
export interface PaymentRequestDto {
  orderId: number;
  amount: number;
  paymentMethod: 'credit_card' | 'debit_card' | 'paypal';
  paymentDetails: string;
}

export interface PaymentResponseDto {
  id: number;
  orderId: number;
  amount: number;
  paymentMethod: string;
  status: 'PENDING' | 'COMPLETED' | 'FAILED' | 'REFUNDED';
  transactionId: string;
  createdAt: string;
}

