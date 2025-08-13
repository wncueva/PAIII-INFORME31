import { BrowserRouter, Routes, Route } from 'react-router-dom';
import FormularioPaciente from './pages/FormularioPaciente';



function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<FormularioPaciente />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
