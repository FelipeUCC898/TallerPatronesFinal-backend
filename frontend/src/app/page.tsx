'use client';

import { useState, useEffect } from 'react';
import Link from 'next/link';
import { tshirtApi } from '@/services/api';
import type { TShirtResponseDto } from '@/types';

export default function Home() {
  const [tshirts, setTshirts] = useState<TShirtResponseDto[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [filter, setFilter] = useState<{ category?: string; size?: string; color?: string }>({});

  useEffect(() => {
    loadTShirts();
  }, [filter]);

  const loadTShirts = async () => {
    try {
      setLoading(true);
      setError(null);
      let data: TShirtResponseDto[];

      if (filter.category) {
        data = await tshirtApi.getByCategory(filter.category);
      } else if (filter.size) {
        data = await tshirtApi.getBySize(filter.size);
      } else if (filter.color) {
        data = await tshirtApi.getByColor(filter.color);
      } else {
        data = await tshirtApi.getAll();
      }

      setTshirts(data);
    } catch (err: any) {
      setError(err.response?.data?.message || 'Error al cargar las camisetas');
    } finally {
      setLoading(false);
    }
  };

  const clearFilters = () => {
    setFilter({});
  };

  return (
    <div className="container">
      <div className="header">
        <h1>🎽 T-Shirt Store</h1>
        <p>Personaliza tu camiseta perfecta</p>
        <nav className="nav">
          <Link href="/">Inicio</Link>
          <Link href="/create">Crear Camiseta</Link>
          <Link href="/orders">Órdenes</Link>
        </nav>
      </div>

      <div className="card">
        <h2>Filtros</h2>
        <div style={{ display: 'flex', gap: '1rem', flexWrap: 'wrap', marginTop: '1rem' }}>
          <select
            className="select"
            style={{ width: '200px' }}
            value={filter.category || ''}
            onChange={(e) => setFilter({ ...filter, category: e.target.value || undefined })}
          >
            <option value="">Todas las categorías</option>
            <option value="casual">Casual</option>
            <option value="sport">Sport</option>
            <option value="premium">Premium</option>
          </select>

          <select
            className="select"
            style={{ width: '150px' }}
            value={filter.size || ''}
            onChange={(e) => setFilter({ ...filter, size: e.target.value || undefined })}
          >
            <option value="">Todas las tallas</option>
            <option value="S">S</option>
            <option value="M">M</option>
            <option value="L">L</option>
            <option value="XL">XL</option>
          </select>

          <input
            type="text"
            className="input"
            style={{ width: '200px' }}
            placeholder="Filtrar por color..."
            value={filter.color || ''}
            onChange={(e) => setFilter({ ...filter, color: e.target.value || undefined })}
          />

          <button className="btn btn-secondary" onClick={clearFilters}>
            Limpiar Filtros
          </button>
        </div>
      </div>

      {error && <div className="error">{error}</div>}

      {loading ? (
        <div className="loading">Cargando camisetas...</div>
      ) : (
        <>
          {tshirts.length === 0 ? (
            <div className="card">
              <p>No se encontraron camisetas.</p>
            </div>
          ) : (
            <div className="grid">
              {tshirts.map((tshirt) => (
                <div key={tshirt.id} className="card">
                  <h3>{tshirt.print}</h3>
                  <p><strong>Categoría:</strong> {tshirt.category}</p>
                  <p><strong>Talla:</strong> {tshirt.size}</p>
                  <p><strong>Color:</strong> {tshirt.color}</p>
                  <p><strong>Tela:</strong> {tshirt.fabric}</p>
                  <p><strong>SKU:</strong> {tshirt.sku}</p>
                  <p><strong>Precio:</strong> ${tshirt.price.toFixed(2)}</p>
                  <Link href={`/tshirt/${tshirt.id}`}>
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

