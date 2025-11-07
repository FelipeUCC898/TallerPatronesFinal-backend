import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "T-Shirt Store - Personaliza tu Camiseta",
  description: "Tienda de camisetas personalizadas con patrones de diseño",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="es">
      <body>{children}</body>
    </html>
  );
}

