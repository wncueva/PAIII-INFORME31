import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

import 'primeicons/primeicons.css';                     // Iconos (primero)
import 'primereact/resources/primereact.min.css';       // Componentes base
import 'primereact/resources/themes/lara-light-blue/theme.css'; // Tema: lara-light-blue (recomendado)
import 'primeflex/primeflex.css';                       // PrimeFlex (opcional)

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

reportWebVitals();