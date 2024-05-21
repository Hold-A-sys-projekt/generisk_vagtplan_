import * as React from "react"

import {
  Select as ChadSelect,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"

export default function Select({ items, title, defaultValue }) {
    console.log(items)
    return (
    <ChadSelect defaultValue={defaultValue}>
      <SelectTrigger className="w-[180px]">
        <SelectValue placeholder={defaultValue} />
      </SelectTrigger>
      <SelectContent>
        <SelectGroup>
          <SelectLabel>{title}</SelectLabel>
          {items.map((item) => (
              <SelectItem key={item.value} value={item.value}>{item.value}</SelectItem>
            ))
          }
        </SelectGroup>
      </SelectContent>
    </ChadSelect>
  )
}

