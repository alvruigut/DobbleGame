import { formValidators } from "../../../validators/formValidators";

export const PlayerEditInputs = [
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
    },
]