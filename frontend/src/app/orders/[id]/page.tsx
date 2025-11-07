'use client';

import { useState, useEffect } from 'react';
import { useParams, useRouter } from 'next/navigation';
import Link from 'next/link';
import { orderApi, paymentApi } from '@/services/api';
import type { OrderResponseDto, PaymentRequestDto } from '@/types';

export default function OrderDetail() {
  const params = useParams();
  const router = useRouter();
  const id = parseInt(params.id as string);
  const [order, setOrder] = useState<OrderResponseDto | null>(null);
  const [loading, setLoading] = useState(true);
  const [processingPayment, setProcessingPayment] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [paymentForm, setPaymentForm] = useState<PaymentRequestDto>({
    orderId: id,
    amount: 0,
    paymentMethod: 'credit_card',
    paymentDetails: '',
  });

  useEffect(() => {
    if (id) {
      loadOrder();
    }
  }, [id]);

  useEffect(() => {
    if (order) {
      setPaymentForm((prev) => ({
        ...prev,
        orderId: order.id,
        amount: order.totalAmount,
      }));
    }
  }, [order]);

  const loadOrder = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await orderApi.getById(id);
      setOrder(data);
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al cargar la orden');
    } finally {
      setLoading(false);
    }
  };

  const handlePayment = async (e: React.FormEvent) => {
    e.preventDefault();
    setProcessingPayment(true);
    setError(null);

    try {
      await paymentApi.process(id, paymentForm);
      alert('Pago procesado exitosamente');
      loadOrder();
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al procesar el pago');
    } finally {
      setProcessingPayment(false);
    }
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'CONFIRMED':
        return '#27ae60';
      case 'SHIPPED':
        return '#3498db';
      case 'DELIVERED':
        return '#2ecc71';
      case 'CANCELLED':
        return '#e74c3c';
      default:
        return '#f39c12';
    }
  };

  if (loading) {
    return (
      <div className="container">
        <div className="loading">Cargando...</div>
      </div>
    );
  }

  if (error && !order) {
    return (
      <div className="container">
        <div className="error">{error}</div>
        <Link href="/orders">
          <button className="btn btn-primary">Volver a órdenes</button>
        </Link>
      </div>
    );
  }

  if (!order) {
    return (
      <div className="container">
        <div className="error">Orden no encontrada</div>
        <Link href="/orders">
          <button className="btn btn-primary">Volver a órdenes</button>
        </Link>
      </div>
    );
  }

  return (
    <div className="container">
      <div className="header">
        <h1>Orden #{order.id}</h1>
        <a href="/orders" style={{ color: '#667eea' }}>← Volver a órdenes</a>
      </div>

      <div className="card" style={{ maxWidth: '800px', margin: '0 auto' }}>
        <h2>Información de la Orden</h2>
        <div style={{ marginTop: '1.5rem' }}>
          <p><strong>Cliente:</strong> {order.customerName}</p>
          <p><strong>Email:</strong> {order.customerEmail}</p>
          <p>
            <strong>Estado:</strong>{' '}
            <span style={{ color: getStatusColor(order.status) }}>
              {order.status}
            </span>
          </p>
          <p><strong>Total:</strong> ${order.totalAmount.toFixed(2)}</p>
          <p><strong>Fecha:</strong> {new Date(order.createdAt).toLocaleString()}</p>
        </div>

        <h3 style={{ marginTop: '2rem' }}>Items</h3>
        <div style={{ marginTop: '1rem' }}>
          {order.items.map((item, index) => (
            <div key={index} className="card" style={{ background: '#f9f9f9', marginBottom: '1rem' }}>
              <p><strong>Item {index + 1}</strong></p>
              <p>Camiseta ID: {item.tshirtId}</p>
              <p>Cantidad: {item.quantity}</p>
              <p>Subtotal: ${item.subtotal.toFixed(2)}</p>
            </div>
          ))}
        </div>
      </div>

      {order.status === 'PENDING' && (
        <div className="card" style={{ maxWidth: '800px', margin: '2rem auto 0' }}>
          <h2>Procesar Pago</h2>
          {error && <div className="error">{error}</div>}
          
          <form onSubmit={handlePayment} style={{ marginTop: '1.5rem' }}>
            <div className="form-group">
              <label>Método de Pago *</label>
              <select
                className="select"
                value={paymentForm.paymentMethod}
                onChange={(e) => setPaymentForm({ ...paymentForm, paymentMethod: e.target.value as any })}
                required
              >
                <option value="credit_card">Tarjeta de Crédito</option>
                <option value="debit_card">Tarjeta de Débito</option>
                <option value="paypal">PayPal</option>
              </select>
            </div>

            <div className="form-group">
              <label>Monto *</label>
              <input
                type="number"
                className="input"
                value={paymentForm.amount}
                onChange={(e) => setPaymentForm({ ...paymentForm, amount: parseFloat(e.target.value) || 0 })}
                required
                min="0.01"
                step="0.01"
              />
            </div>

            <div className="form-group">
              <label>Detalles del Pago *</label>
              <input
                type="text"
                className="input"
                value={paymentForm.paymentDetails}
                onChange={(e) => setPaymentForm({ ...paymentForm, paymentDetails: e.target.value })}
                required
                placeholder="Ej: Número de tarjeta, cuenta PayPal, etc."
              />
            </div>

            <button
              type="submit"
              className="btn btn-primary"
              disabled={processingPayment}
              style={{ width: '100%' }}
            >
              {processingPayment ? 'Procesando...' : 'Procesar Pago'}
            </button>
          </form>
        </div>
      )}
    </div>
  );
}

