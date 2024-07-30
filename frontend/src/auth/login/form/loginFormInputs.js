import { formValidators } from "../../../validators/formValidators";

export const loginFormInputs = [
  {
    tag: "Nombre de Usuario",
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
];