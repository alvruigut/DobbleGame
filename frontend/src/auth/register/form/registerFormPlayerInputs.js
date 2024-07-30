import { formValidators } from "../../../validators/formValidators";
import { registerFormPersonInputs } from "./registerFormPersonInputs";

export const registerFormPlayerInputs = [
    ...registerFormPersonInputs,
    // {
    //     tag: "Avatar",
    //     name: "avatar",
    //     type: "file",
    //     isRequired: true,
    //     validators: [formValidators.notEmptyValidator],
    // },
]