import type { AbstractControl, ValidatorFn } from '@angular/forms';

export function fileTypeValidator(allowedTypes: string[]): ValidatorFn {
  return (control: AbstractControl) => {
    const file = control.value as File;
    if (file) {
      const fileType = file.type;
      if (!allowedTypes.includes(fileType)) {
        return { invalidFileType: true };
      }
    }
    return null; // Return null if validation passes
  };
}
