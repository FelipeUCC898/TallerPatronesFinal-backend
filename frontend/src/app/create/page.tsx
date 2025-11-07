'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { tshirtApi } from '@/services/api';
import type { TShirtCustomizationDto } from '@/types';

export default function CreateTShirt() {
  const router = useRouter();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState(false);
  const [formData, setFormData] = useState<TShirtCustomizationDto>({
    size: 'M',
    color: '',
    print: '',
    fabric: 'cotton',
    price: 25.00,
    sku: '',
    category: 'casual',
  });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setSuccess(false);

    try {
      // Generate SKU if not provided
      if (!formData.sku) {
        const timestamp = Date.now();
        formData.sku = `${formData.category.toUpperCase()}-${timestamp}`;
      }

      await tshirtApi.create(formData);
      setSuccess(true);
      setTimeout(() => {
        router.push('/');
      }, 2000);
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al crear la camiseta');
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === 'price' ? parseFloat(value) || 0 : value,
    }));
  };

  return (
    <div className="container">
      <div className="header">
        <h1>Crear Nueva Camiseta</h1>
        <a href="/" style={{ color: '#667eea' }}>← Volver al inicio</a>
      </div>

      <div className="card" style={{ maxWidth: '600px', margin: '0 auto' }}>
        {error && <div className="error">{error}</div>}
        {success && <div className="success">¡Camiseta creada exitosamente! Redirigiendo...</div>}

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Categoría *</label>
            <select
              name="category"
              className="select"
              value={formData.category}
              onChange={handleChange}
              required
            >
              <option value="casual">Casual</option>
              <option value="sport">Sport</option>
              <option value="premium">Premium</option>
            </select>
          </div>

          <div className="form-group">
            <label>Talla *</label>
            <select
              name="size"
              className="select"
              value={formData.size}
              onChange={handleChange}
              required
            >
              <option value="S">S</option>
              <option value="M">M</option>
              <option value="L">L</option>
              <option value="XL">XL</option>
            </select>
          </div>

          <div className="form-group">
            <label>Color *</label>
            <input
              type="text"
              name="color"
              className="input"
              value={formData.color}
              onChange={handleChange}
              required
              placeholder="Ej: Azul, Rojo, Negro..."
            />
          </div>

          <div className="form-group">
            <label>Diseño/Print *</label>
            <input
              type="text"
              name="print"
              className="input"
              value={formData.print}
              onChange={handleChange}
              required
              placeholder="Ej: Logo Java, Estrella..."
            />
          </div>

          <div className="form-group">
            <label>Tela *</label>
            <select
              name="fabric"
              className="select"
              value={formData.fabric}
              onChange={handleChange}
              required
            >
              <option value="cotton">Cotton</option>
              <option value="polyester">Polyester</option>
              <option value="blend">Blend</option>
            </select>
          </div>

          <div className="form-group">
            <label>Precio Base *</label>
            <input
              type="number"
              name="price"
              className="input"
              value={formData.price}
              onChange={handleChange}
              required
              min="0.01"
              step="0.01"
            />
          </div>

          <div className="form-group">
            <label>SKU (opcional, se generará automáticamente si está vacío)</label>
            <input
              type="text"
              name="sku"
              className="input"
              value={formData.sku}
              onChange={handleChange}
              placeholder="Dejar vacío para auto-generar"
            />
          </div>

          <button
            type="submit"
            className="btn btn-primary"
            disabled={loading}
            style={{ width: '100%' }}
          >
            {loading ? 'Creando...' : 'Crear Camiseta'}
          </button>
        </form>
      </div>
    </div>
  );
}

