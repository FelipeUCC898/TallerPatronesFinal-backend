'use client';

import { useState, useEffect } from 'react';
import Link from 'next/link';
import { orderApi } from '@/services/api';
import type { OrderResponseDto } from '@/types';

export default function OrdersPage() {
  const [orders, setOrders] = useState<OrderResponseDto[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [customerEmail, setCustomerEmail] = useState('');

  useEffect(() => {
    loadOrders();
  }, []);

  const loadOrders = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await orderApi.getAll();
      setOrders(data);
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al cargar las órdenes');
    } finally {
      setLoading(false);
    }
  };

  const loadOrdersByEmail = async () => {
    if (!customerEmail.trim()) {
      loadOrders();
      return;
    }

    try {
      setLoading(true);
      setError(null);
      const data = await orderApi.getByCustomerEmail(customerEmail);
      setOrders(data);
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al cargar las órdenes');
    } finally {
      setLoading(false);
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

  return (
    <div className="container">
      <div className="header">
        <h1>Órdenes</h1>
        <nav className="nav">
          <Link href="/">Inicio</Link>
          <Link href="/create">Crear Camiseta</Link>
          <Link href="/orders">Órdenes</Link>
          <Link href="/orders/create">Nueva Orden</Link>
        </nav>
      </div>

      <div className="card">
        <h2>Buscar Órdenes</h2>
        <div style={{ display: 'flex', gap: '1rem', marginTop: '1rem' }}>
          <input
            type="email"
            className="input"
            placeholder="Email del cliente..."
            value={customerEmail}
            onChange={(e) => setCustomerEmail(e.target.value)}
            style={{ flex: 1 }}
          />
          <button className="btn btn-primary" onClick={loadOrdersByEmail}>
            Buscar
          </button>
          <button className="btn btn-secondary" onClick={loadOrders}>
            Ver Todas
          </button>
        </div>
      </div>

      {error && <div className="error">{error}</div>}

      {loading ? (
        <div className="loading">Cargando órdenes...</div>
      ) : (
        <>
          {orders.length === 0 ? (
            <div className="card">
              <p>No se encontraron órdenes.</p>
              <Link href="/orders/create">
                <button className="btn btn-primary" style={{ marginTop: '1rem' }}>
                  Crear Nueva Orden
                </button>
              </Link>
            </div>
          ) : (
            <div className="grid">
              {orders.map((order) => (
                <div key={order.id} className="card">
                  <h3>Orden #{order.id}</h3>
                  <p><strong>Cliente:</strong> {order.customerName}</p>
                  <p><strong>Email:</strong> {order.customerEmail}</p>
                  <p>
                    <strong>Estado:</strong>{' '}
                    <span style={{ color: getStatusColor(order.status) }}>
                      {order.status}
                    </span>
                  </p>
                  <p><strong>Total:</strong> ${order.totalAmount.toFixed(2)}</p>
                  <p><strong>Items:</strong> {order.items.length}</p>
                  <p><strong>Fecha:</strong> {new Date(order.createdAt).toLocaleDateString()}</p>
                  <Link href={`/orders/${order.id}`}>
                    <button className="btn btn-primary" style={{ marginTop: '1rem', width: '100%' }}>
                      Ver Detalles
                    </button>
                  </Link>
                </div>
              ))}
            </div>
          )}
        </>
      )}
    </div>
  );
}

