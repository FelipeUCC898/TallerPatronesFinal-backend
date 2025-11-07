# T-Shirt Store Frontend

Frontend de la aplicación T-Shirt Store construido con Next.js y TypeScript.

## 🚀 Instalación

1. Instalar dependencias:
```bash
npm install
```

2. Configurar la URL del backend (opcional):
Crear un archivo `.env.local`:
```
NEXT_PUBLIC_API_URL=https://tallerpatronesfinal-backend-production.up.railway.app/api
```

**Nota**: Por defecto, el frontend está configurado para usar el backend en Railway. Si quieres usar localhost, crea el archivo `.env.local` con la URL local.

3. Ejecutar en modo desarrollo:
```bash
npm run dev
```

La aplicación estará disponible en `http://localhost:3000`

## 📁 Estructura del Proyecto

```
frontend/
├── src/
│   ├── app/              # Páginas y rutas de Next.js
│   │   ├── page.tsx      # Página principal (listado de camisetas)
│   │   ├── create/       # Crear nueva camiseta
│   │   ├── orders/       # Gestión de órdenes
│   │   └── tshirt/       # Detalles de camiseta
│   ├── services/         # Servicios API
│   │   └── api.ts        # Cliente API para backend
│   └── types/            # Tipos TypeScript
│       └── index.ts      # Definiciones de tipos
├── public/               # Archivos estáticos
└── package.json          # Dependencias
```

## 🔌 Conexión con el Backend

El frontend se conecta al backend Spring Boot en `http://localhost:8080` por defecto.

### Endpoints utilizados:

- **T-Shirts**: `/api/tshirts`
- **Orders**: `/api/orders`
- **Payments**: `/api/payments`

## 🛠️ Tecnologías

- **Next.js 14**: Framework React
- **TypeScript**: Tipado estático
- **Axios**: Cliente HTTP
- **CSS Modules**: Estilos

## 📝 Scripts Disponibles

- `npm run dev`: Inicia el servidor de desarrollo
- `npm run build`: Construye la aplicación para producción
- `npm run start`: Inicia el servidor de producción
- `npm run lint`: Ejecuta el linter

## 🎨 Características

- ✅ Listado de camisetas con filtros
- ✅ Crear nuevas camisetas personalizadas
- ✅ Gestión de órdenes
- ✅ Procesamiento de pagos
- ✅ Diseño responsive y moderno
