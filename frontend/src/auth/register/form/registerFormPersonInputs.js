import { formValidators } from "../../../validators/formValidators";

export const registerFormPersonInputs = [
    {
        tag: "Nombre",
        name: "username",
        type: "text",
        defaultValue: "",
        isRequired: true,
        validators: [formValidators.notEmptyValidator],
    },
    {
        tag: "Contraseña",
        name: "password",
        type: "password",
        defaultValue: "",
        isRequired: true,
        validators: [formValidators.notEmptyValidator],
    },
    {
        tag: "Nombre",
        name: "firstName",
        type: "text",
        defaultValue: "",
        isRequired: true,
        validators: [formValidators.notEmptyValidator],
    },
    {
        tag: "Apellido",
        name: "lastName",
        type: "text",
        defaultValue: "",
        isRequired: true,
        validators: [formValidators.notEmptyValidator],
    },
    {
        tag: "Email",
        name: "email",
        text: 'text',
        defaultValue: "",
        isRequired: true,
        validators: [formValidators.notEmptyValidator],
    },
    {
        tag: "Número de teléfono",
        name: "telephone",
        type: "text",
        defaultValue: "",
        isRequired:true,
        validators: [formValidators.notEmptyValidator, formValidators.telephoneValidator],
    }
]