import * as React from "react"

import { cn } from "../../lib/utils"

const Select = React.forwardRef(({ className, type, ...props }, ref) => {
  return (
    (<select
      type={type}
      className={cn(
        "appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-700 bg-gray-700 text-white focus:outline-none focus:ring-[#ea6b28] focus:border-[#ea6b28] sm:text-sm",
        className
      )}
      ref={ref}
      {...props}>
        <option>Chips</option>
        <option>Eis</option>
        <option>Nudeln</option>
        <option>Milch</option>
        <option>Kaffee</option>
      </select>)
  );
})
Select.displayName = "Select"

export { Select }
