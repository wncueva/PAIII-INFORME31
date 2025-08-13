import React, { useState } from 'react';
import axios from 'axios';
import { InputText } from 'primereact/inputtext';
import { Calendar } from 'primereact/calendar';
import { Button } from 'primereact/button';
import '../style/FormularioPaciente.css'; 

interface FormData {
    nombre: string;
    apellido: string;
    fechaNacimiento: Date | null;
    email: string;
}

interface Errors {
    nombre: string;
    apellido: string;
    fechaNacimiento: string;
    email: string;
}

export default function FormularioPaciente() {
    const [formData, setFormData] = useState<FormData>({
        nombre: '',
        apellido: '',
        fechaNacimiento: null,
        email: ''
    });

    const [errors, setErrors] = useState<Errors>({
        nombre: '',
        apellido: '',
        fechaNacimiento: '',
        email: ''
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
        setErrors({ ...errors, [name]: '' }); // Limpiar error para el campo modificado
    };

    const handleDateChange = (e: any) => {
        console.log('Selected date:', e.value); // Depuración: Registrar fecha seleccionada
        setFormData({ ...formData, fechaNacimiento: e.value });
        setErrors({ ...errors, fechaNacimiento: '' }); // Limpiar error de fecha
    };
    

    const validateForm = () => {
        let isValid = true;
        const newErrors: Errors = {
            nombre: '',
            apellido: '',
            fechaNacimiento: '',
            email: ''
        };

        if (!formData.nombre.trim()) {
            newErrors.nombre = 'El nombre es requerido';
            isValid = false;
        }
        if (!formData.apellido.trim()) {
            newErrors.apellido = 'El apellido es requerido';
            isValid = false;
        }
        if (!formData.fechaNacimiento) {
            newErrors.fechaNacimiento = 'La fecha de nacimiento es requerida';
            isValid = false;
        }
        if (!formData.email.trim()) {
            newErrors.email = 'El email es requerido';
            isValid = false;
        } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
            newErrors.email = 'El email no es válido';
            isValid = false;
        }

        setErrors(newErrors);
        return isValid;
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        if (!validateForm()) {
            return;
        }

        try {
            // Convertir fecha a formato yyyy-mm-dd
            const fechaFormateada = formData.fechaNacimiento
                ? `${formData.fechaNacimiento.getFullYear()}-${String(formData.fechaNacimiento.getMonth() + 1).padStart(2, '0')}-${String(formData.fechaNacimiento.getDate()).padStart(2, '0')}`
                : '';
            console.log('Formatted date:', fechaFormateada); // Depuración: Registrar fecha formateada
            console.log('Payload:', {
                nombre: formData.nombre,
                apellido: formData.apellido,
                fechaNacimiento: fechaFormateada,
                email: formData.email
            }); // Depuración: Registrar payload completo

            const payload = {
                nombre: formData.nombre,
                apellido: formData.apellido,
                fechaNacimiento: fechaFormateada,
                email: formData.email
            };

            const response = await axios.post('http://localhost:8080/api/pacientes', payload);
            console.log('Paciente guardado:', response.data);
            alert('¡Paciente registrado correctamente!');

            // Limpiar formulario
            setFormData({
                nombre: '',
                apellido: '',
                fechaNacimiento: null,
                email: ''
            });
            setErrors({
                nombre: '',
                apellido: '',
                fechaNacimiento: '',
                email: ''
            });
        } catch (error: any) {
            console.error('Error al guardar el paciente:', error);
            const errorMessage = error.response?.data?.message || 'Verifique los datos e intente nuevamente';
            alert(`Error al registrar el paciente: ${errorMessage}`);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="formulario-paciente">
            <div className="field">
                <label htmlFor="nombre" className="block">Nombre</label>
                <InputText
                    id="nombre"
                    name="nombre"
                    value={formData.nombre}
                    onChange={handleChange}
                    className={`w-full ${errors.nombre ? 'p-invalid' : ''}`}
                />
                {errors.nombre && <small className="p-error block">{errors.nombre}</small>}
            </div>

            <div className="field">
                <label htmlFor="apellido" className="block">Apellido</label>
                <InputText
                    id="apellido"
                    name="apellido"
                    value={formData.apellido}
                    onChange={handleChange}
                    className={`w-full ${errors.apellido ? 'p-invalid' : ''}`}
                />
                {errors.apellido && <small className="p-error block">{errors.apellido}</small>}
            </div>

            <div className="field">
                <label htmlFor="fechaNacimiento" className="block">Fecha de Nacimiento</label>
                <Calendar
                    value={formData.fechaNacimiento}
                    onChange={handleDateChange}
                    dateFormat="yy-mm-dd"
                    appendTo="self"
                    className={`w-full ${errors.fechaNacimiento ? 'p-invalid' : ''}`}
                    showIcon
                    icon="pi pi-calendar"
                />
                {errors.fechaNacimiento && <small className="p-error block">{errors.fechaNacimiento}</small>}
            </div>

            <div className="field">
                <label htmlFor="email" className="block">Email</label>
                <InputText
                    id="email"
                    name="email"
                    type="email"
                    value={formData.email}
                    onChange={handleChange}
                    className={`w-full ${errors.email ? 'p-invalid' : ''}`}
                />
                {errors.email && <small className="p-error block">{errors.email}</small>}
            </div>

            <Button label="Guardar" icon="pi pi-check" type="submit" />
        </form>
    );
}