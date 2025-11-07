'use client';

import { useState, useEffect } from 'react';
import { useParams, useRouter } from 'next/navigation';
import Link from 'next/link';
import { tshirtApi } from '@/services/api';
import type { TShirtResponseDto } from '@/types';

export default function TShirtDetail() {
  const params = useParams();
  const router = useRouter();
  const id = parseInt(params.id as string);
  const [tshirt, setTShirt] = useState<TShirtResponseDto | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (id) {
      loadTShirt();
    }
  }, [id]);

  const loadTShirt = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await tshirtApi.getById(id);
      setTShirt(data);
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al cargar la camiseta');
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="container">
        <div className="loading">Cargando...</div>
      </div>
    );
  }

  if (error || !tshirt) {
    return (
      <div className="container">
        <div className="error">{error || 'Camiseta no encontrada'}</div>
        <Link href="/">
          <button className="btn btn-primary">Volver al inicio</button>
        </Link>
      </div>
    );
  }

  return (
    <div className="container">
      <div className="header">
        <h1>Detalles de la Camiseta</h1>
        <a href="/" style={{ color: '#667eea' }}>← Volver al inicio</a>
      </div>

      <div className="card" style={{ maxWidth: '600px', margin: '0 auto' }}>
        <h2>{tshirt.print}</h2>
        <div style={{ marginTop: '1.5rem' }}>
          <p><strong>ID:</strong> {tshirt.id}</p>
          <p><strong>Categoría:</strong> {tshirt.category}</p>
          <p><strong>Talla:</strong> {tshirt.size}</p>
          <p><strong>Color:</strong> {tshirt.color}</p>
          <p><strong>Tela:</strong> {tshirt.fabric}</p>
          <p><strong>SKU:</strong> {tshirt.sku}</p>
          <p style={{ fontSize: '1.5rem', fontWeight: 'bold', color: '#667eea', marginTop: '1rem' }}>
            <strong>Precio:</strong> ${tshirt.price.toFixed(2)}
          </p>
        </div>
      </div>
    </div>
  );
}

