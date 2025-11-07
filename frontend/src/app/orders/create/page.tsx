'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { orderApi, tshirtApi } from '@/services/api';
import type { OrderRequestDto, OrderItemDto, TShirtResponseDto } from '@/types';

export default function CreateOrder() {
  const router = useRouter();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState(false);
  const [tshirts, setTshirts] = useState<TShirtResponseDto[]>([]);
  const [orderItems, setOrderItems] = useState<OrderItemDto[]>([]);
  const [customerName, setCustomerName] = useState('');
  const [customerEmail, setCustomerEmail] = useState('');

  useEffect(() => {
    loadTShirts();
  }, []);

  const loadTShirts = async () => {
    try {
      const data = await tshirtApi.getAll();
      setTshirts(data);
    } catch (err: any) {
      setError('Error al cargar las camisetas');
    }
  };

  const addItem = () => {
    if (tshirts.length > 0) {
      setOrderItems([...orderItems, { tshirtId: tshirts[0].id, quantity: 1 }]);
    }
  };

  const removeItem = (index: number) => {
    setOrderItems(orderItems.filter((_, i) => i !== index));
  };

  const updateItem = (index: number, field: keyof OrderItemDto, value: any) => {
    const updated = [...orderItems];
    updated[index] = { ...updated[index], [field]: value };
    setOrderItems(updated);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (orderItems.length === 0) {
      setError('Debe agregar al menos un item a la orden');
      return;
    }

    setLoading(true);
    setError(null);
    setSuccess(false);

    try {
      const order: OrderRequestDto = {
        items: orderItems,
        customerName,
        customerEmail,
      };

      await orderApi.create(order);
      setSuccess(true);
      setTimeout(() => {
        router.push('/orders');
      }, 2000);
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al crear la orden');
    } finally {
      setLoading(false);
    }
  };

  const getTShirtById = (id: number) => {
    return tshirts.find((t) => t.id === id);
  };

  const calculateTotal = () => {
    return orderItems.reduce((total, item) => {
      const tshirt = getTShirtById(item.tshirtId);
      return total + (tshirt ? tshirt.price * item.quantity : 0);
    }, 0);
  };

  return (
    <div className="container">
      <div className="header">
        <h1>Crear Nueva Orden</h1>
        <a href="/orders" style={{ color: '#667eea' }}>← Volver a órdenes</a>
      </div>

      <div className="card" style={{ maxWidth: '800px', margin: '0 auto' }}>
        {error && <div className="error">{error}</div>}
        {success && <div className="success">¡Orden creada exitosamente! Redirigiendo...</div>}

        <form onSubmit={handleSubmit}>
          <h2 style={{ marginBottom: '1.5rem' }}>Información del Cliente</h2>
          
          <div className="form-group">
            <label>Nombre del Cliente *</label>
            <input
              type="text"
              className="input"
              value={customerName}
              onChange={(e) => setCustomerName(e.target.value)}
              required
              placeholder="Ej: Juan Pérez"
            />
          </div>

          <div className="form-group">
            <label>Email del Cliente *</label>
            <input
              type="email"
              className="input"
              value={customerEmail}
              onChange={(e) => setCustomerEmail(e.target.value)}
              required
              placeholder="ejemplo@email.com"
            />
          </div>

          <h2 style={{ marginBottom: '1.5rem', marginTop: '2rem' }}>Items de la Orden</h2>

          {orderItems.map((item, index) => {
            const tshirt = getTShirtById(item.tshirtId);
            return (
              <div key={index} className="card" style={{ marginBottom: '1rem', background: '#f9f9f9' }}>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
                  <h3>Item {index + 1}</h3>
                  <button
                    type="button"
                    className="btn btn-danger"
                    onClick={() => removeItem(index)}
                  >
                    Eliminar
                  </button>
                </div>

                <div className="form-group">
                  <label>Camiseta *</label>
                  <select
                    className="select"
                    value={item.tshirtId}
                    onChange={(e) => updateItem(index, 'tshirtId', parseInt(e.target.value))}
                    required
                  >
                    <option value="">Seleccionar camiseta...</option>
                    {tshirts.map((t) => (
                      <option key={t.id} value={t.id}>
                        {t.print} - {t.size} - ${t.price.toFixed(2)}
                      </option>
                    ))}
                  </select>
                </div>

                <div className="form-group">
                  <label>Cantidad *</label>
                  <input
                    type="number"
                    className="input"
                    value={item.quantity}
                    onChange={(e) => updateItem(index, 'quantity', parseInt(e.target.value))}
                    required
                    min="1"
                    max="100"
                  />
                </div>

                {tshirt && (
                  <p style={{ marginTop: '0.5rem', color: '#666' }}>
                    Subtotal: ${(tshirt.price * item.quantity).toFixed(2)}
                  </p>
                )}
              </div>
            );
          })}

          <button
            type="button"
            className="btn btn-secondary"
            onClick={addItem}
            style={{ marginBottom: '1.5rem' }}
          >
            + Agregar Item
          </button>

          <div className="card" style={{ background: '#e8f5e9', marginBottom: '1.5rem' }}>
            <h3>Total: ${calculateTotal().toFixed(2)}</h3>
          </div>

          <button
            type="submit"
            className="btn btn-primary"
            disabled={loading || orderItems.length === 0}
            style={{ width: '100%' }}
          >
            {loading ? 'Creando...' : 'Crear Orden'}
          </button>
        </form>
      </div>
    </div>
  );
}

